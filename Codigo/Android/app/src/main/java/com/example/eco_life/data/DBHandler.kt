package com.example.eco_life.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.eco_life.EmissionModel

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ("
                + "$ID_COL INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$EMISSION_FACTOR_COL REAL, "
                + "$EMISSION_VALUE_COL REAL, "
                + "$DATE_COL TEXT, "
                + "$TYPE_COL TEXT, "
                + "$HOURS_COL REAL)")
        db.execSQL(query)
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

    // Function to delete a specific emission record
    fun deleteEmission(id: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID_COL=?", arrayOf(id.toString()))
        db.close()
    }

    // Read function to retrieve all emissions
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
        onCreate(db)
    }

    companion object {
        private const val DB_NAME = "Eco-Life"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "emissions"
        private const val ID_COL = "id"
        private const val EMISSION_FACTOR_COL = "factor"
        private const val EMISSION_VALUE_COL = "emissionValue"
        private const val DATE_COL = "date"
        private const val TYPE_COL = "type"
        private const val HOURS_COL = "hours"
    }
}