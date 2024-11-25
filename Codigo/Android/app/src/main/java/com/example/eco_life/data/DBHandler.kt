package com.example.eco_life.data

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import co.yml.charts.common.model.Point
import com.example.eco_life.EmissionModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val emissionTableQuery = ("CREATE TABLE $TABLE_NAME ("
                + "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$EMISSION_FACTOR_COL REAL, "
                + "$EMISSION_VALUE_COL REAL, "
                + "$DATE_COL TEXT, "
                + "$TYPE_COL TEXT, "
                + "$HOURS_COL REAL)")
        db.execSQL(emissionTableQuery)

        val userProgressTableQuery = ("CREATE TABLE $USER_PROGRESS_TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY, "
                + "$COLUMN_DAY_STATUS TEXT, "
                + "$COLUMN_CURRENT_STREAK INTEGER, "
                + "$COLUMN_HIGHEST_STREAK INTEGER)")
        db.execSQL(userProgressTableQuery)

        db.execSQL(
            "INSERT INTO $USER_PROGRESS_TABLE_NAME ($COLUMN_ID, $COLUMN_DAY_STATUS, $COLUMN_CURRENT_STREAK, $COLUMN_HIGHEST_STREAK) " +
                    "VALUES (1, 'waiting,waiting,waiting,waiting,waiting,waiting,waiting', 0, 0)"
        )

        // Crear tabla para la puntuación más alta
        val highScoreTableQuery = ("CREATE TABLE $HIGH_SCORE_TABLE_NAME ("
                + "$HIGH_SCORE_ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$HIGH_SCORE_COL INTEGER)")
        db.execSQL(highScoreTableQuery)
        // Insertar un valor inicial de 0 para el récord
        db.execSQL("INSERT INTO $HIGH_SCORE_TABLE_NAME ($HIGH_SCORE_COL) VALUES (0)")

    }

    fun addEmission(
        emissionFactor: Double,
        emissionValue: Double,
        emissionDate: String,
        type: String,
        hours: Double
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(EMISSION_FACTOR_COL, emissionFactor)
        values.put(EMISSION_VALUE_COL, emissionValue)
        values.put(DATE_COL, emissionDate)
        values.put(TYPE_COL, type)
        values.put(HOURS_COL, hours)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteEmission(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID_COL=?", arrayOf(id.toString()))
        db.close()
    }

    fun readEmissions(): List<EmissionModel> {
        val emissionsList = mutableListOf<EmissionModel>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val emission = EmissionModel(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL)),
                    emissionFactor = cursor.getDouble(cursor.getColumnIndexOrThrow(EMISSION_FACTOR_COL)),
                    emissionValue = cursor.getDouble(cursor.getColumnIndexOrThrow(EMISSION_VALUE_COL)),
                    emissionDate = cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL)),
                    type = cursor.getString(cursor.getColumnIndexOrThrow(TYPE_COL)),
                    hours = cursor.getDouble(cursor.getColumnIndexOrThrow(HOURS_COL))
                )
                emissionsList.add(emission)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return emissionsList
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $USER_PROGRESS_TABLE_NAME")
        onCreate(db)
    }

    fun countDistinctDates(): Int {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT COUNT(DISTINCT $DATE_COL) FROM $TABLE_NAME", null)
        var count = 0
        if (cursor.moveToFirst()) {
            count = cursor.getInt(0)
        }
        cursor.close()
        db.close()
        return count
    }

    fun getEmissionData(): Pair<List<String>, List<Float>> {
        val db = this.readableDatabase

        val cursor = db.rawQuery(
            """
        SELECT $DATE_COL, 
               SUM($EMISSION_FACTOR_COL * $EMISSION_VALUE_COL * $HOURS_COL) AS totalEmission
        FROM $TABLE_NAME
        GROUP BY $DATE_COL
        """, null
        )

        val dates = mutableListOf<String>()
        val emissions = mutableListOf<Float>()

        if (cursor.moveToFirst()) {
            do {
                val date = cursor.getString(0)
                val totalEmission = cursor.getFloat(1)
                dates.add(date)
                emissions.add(totalEmission)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return Pair(dates, emissions)
    }

    fun getEmissionsByTypeAndDate(type: String, date: String): Double {
        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT SUM(emissionFactor * emissionValue * hours) FROM emissions WHERE type = ? AND date = ?",
            arrayOf(type, date)
        )
        var sum = 0.0
        if (cursor.moveToFirst()) {
            sum = cursor.getDouble(0)
        }
        cursor.close()
        db.close()
        return sum
    }

    fun getUserProgress(): Triple<Array<String>, Int, Int> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $USER_PROGRESS_TABLE_NAME WHERE $COLUMN_ID = 1", null)

        var dayStatus = Array(7) { "waiting" }
        var currentStreak = 0
        var highestStreak = 0

        if (cursor.moveToFirst()) {
            dayStatus = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAY_STATUS)).split(",").toTypedArray()
            currentStreak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CURRENT_STREAK))
            highestStreak = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HIGHEST_STREAK))
        }
        cursor.close()
        db.close()
        return Triple(dayStatus, currentStreak, highestStreak)
    }

    fun updateUserProgress(dayStatus: Array<String>, currentStreak: Int, highestStreak: Int) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_DAY_STATUS, dayStatus.joinToString(","))
            put(COLUMN_CURRENT_STREAK, currentStreak)
            put(COLUMN_HIGHEST_STREAK, highestStreak)
        }
        db.update(USER_PROGRESS_TABLE_NAME, contentValues, "$COLUMN_ID = ?", arrayOf("1"))
        db.close()
    }

    fun updateHighScore(newScore: Int) {
        val db = this.writableDatabase
        val currentHighScore = getHighScore() // No cerramos aquí
        if (newScore > currentHighScore) {
            val values = ContentValues().apply {
                put(HIGH_SCORE_COL, newScore)
            }
            db.update(HIGH_SCORE_TABLE_NAME, values, null, null)
        }
        // db.close() se elimina
    }

    fun getHighScore(): Int {
        val db = this.readableDatabase
        var highScore = 0
        val cursor = db.rawQuery("SELECT $HIGH_SCORE_COL FROM $HIGH_SCORE_TABLE_NAME LIMIT 1", null)
        if (cursor.moveToFirst()) {
            highScore = cursor.getInt(cursor.getColumnIndexOrThrow(HIGH_SCORE_COL))
        }
        cursor.close() // Solo cerramos el cursor
        // db.close() se elimina
        return highScore
    }






    companion object {
        private const val DB_NAME = "Eco-Life"
        private const val DB_VERSION = 2 //1
        private const val TABLE_NAME = "emissions"
        private const val ID_COL = "id"
        private const val EMISSION_FACTOR_COL = "emissionFactor"
        private const val EMISSION_VALUE_COL = "emissionValue"
        private const val DATE_COL = "date"
        private const val TYPE_COL = "type"
        private const val HOURS_COL = "hours"

        private const val USER_PROGRESS_TABLE_NAME = "userProgress"
        private const val COLUMN_ID = "id"
        private const val COLUMN_DAY_STATUS = "day_status"
        private const val COLUMN_CURRENT_STREAK = "current_streak"
        private const val COLUMN_HIGHEST_STREAK = "highest_streak"

        //GAME
        private const val HIGH_SCORE_TABLE_NAME = "high_scores"
        private const val HIGH_SCORE_ID_COL = "id"
        private const val HIGH_SCORE_COL = "score"

    }
}
