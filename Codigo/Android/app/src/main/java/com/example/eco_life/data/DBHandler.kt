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
                + "$FACTOR_COL TEXT, "
                + "$VALUE_COL TEXT, "
                + "$DATE_COL TEXT)")
        db.execSQL(query)
    }

    // Function to add a new course
    fun addNewCourse(
        emissionFactor: Int?,
        emissionValue: Int?,
        emissionDate: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(FACTOR_COL, emissionFactor)
        values.put(VALUE_COL, emissionValue)
        values.put(DATE_COL, emissionDate)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Read function to retrieve all courses
    fun readEmissions(): List<EmissionModel> {
        val emissionsList = mutableListOf<EmissionModel>()
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val course = EmissionModel(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL)),
                    emissionFactor = cursor.getInt(cursor.getColumnIndexOrThrow(FACTOR_COL)),
                    emissionValue = cursor.getInt(cursor.getColumnIndexOrThrow(VALUE_COL)),
                    emissionDate = cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL))
                )
                emissionsList.add(course)
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
        private const val TABLE_NAME = "trashEmission"
        private const val ID_COL = "id"
        private const val FACTOR_COL = "trashFactor"
        private const val VALUE_COL = "trashValue"
        private const val DATE_COL = "date"
    }
}
