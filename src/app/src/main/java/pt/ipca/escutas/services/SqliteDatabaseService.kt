package pt.ipca.escutas.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.News
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.services.contracts.IDatabaseService
import java.text.SimpleDateFormat
import java.util.*

/**
 * Defines a SQLite implementation of an [IDatabaseService].
 *
 */
class SqliteDatabaseService(context: Context) : IDatabaseService, SQLiteOpenHelper(context, "EscutasDB", null, 1) {

    /**
     * Internal group list cache.
     */
    private var outputList: ArrayList<Any> = arrayListOf()

    /**
     * This method is called the first time a database is accessed.
     *
     * @param db
     */
    override fun onCreate(db: SQLiteDatabase?) {

        // Create User Table
        val createUserTable = "CREATE TABLE users (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "photo VARCHAR(256)," +
            "email VARCHAR(256)," +
            "birthday VARCHAR(256)," +
            "name VARCHAR(256)," +
            "groupName VARCHAR(256))"

        // Create User Table
        val createNewsTable = "CREATE TABLE news (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "title VARCHAR(256)," +
            "body VARCHAR(256)," +
            "details VARCHAR(256)," +
            "image VARCHAR(256))"

        // Create Group Table
        val createGroupTable = "CREATE TABLE groups (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(256)," +
            "description VARCHAR(256)," +
            "latitude REAL," +
            "longitude REAL)"

        // Create Event Table
        val createEventTable = "CREATE TABLE events (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(256)," +
            "description VARCHAR(256)," +
            "startDate TEXT," +
            "endDate TEXT," +
            "attachment VARCHAR(256)," +
            "shared INTEGER)"

        db?.execSQL(createUserTable)
        db?.execSQL(createNewsTable)
        db?.execSQL(createGroupTable)
        db?.execSQL(createEventTable)
    }

    /**
     * This method is called if the database version number changes.
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    /**
     * Adds a new document [record] to a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param record The record represents the document.
     * @param callback The callback to control async request.
     */
    override fun addRecord(model: String, record: Any, callback: GenericCallback) {
        val db = this.writableDatabase
        db.insert(model, null, record as ContentValues?)
        db.close()
    }

    /**
     * Updates a record [record] associated to a filter [whereClause] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param whereClause The whereClause represents the record filter.
     * @param record The record represents the document.
     */
    override fun updateRecord(model: String, whereClause: String, record: Any) {
        val db = this.readableDatabase
        val success = db.update(model, record as ContentValues?, whereClause, null)
        db.close()
    }

    /**
     * Deletes a record via [whereClause] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param whereClause The whereClause represents the record filter.
     */
    override fun deleteRecord(model: String, whereClause: String?) {
        val db = this.readableDatabase
        val success = db.delete(model, whereClause, null)
        db.close()
    }

    /**
     * Retrieves all records of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @return
     */
    override fun getAllRecords(model: String, callback: GenericCallback) {
        val selectQuery = "SELECT * FROM $model"
        val db = this.readableDatabase
        outputList.clear()
        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    getRecord(cursor, model, outputList)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            db.close()
        }
        db.close()
        callback.onCallback(outputList)
    }

    /**
     * Retrieves all records that respect an equal filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @param callback The callback to control async request.
     * @return
     */
    override fun getRecordWithEqualFilter(
        model: String,
        recordKey: String,
        recordValue: Any,
        callback: GenericCallback
    ) {
        val selectQuery = "SELECT * FROM $model where " + recordKey + "=" + recordValue
        prepareRecordQuery(model, selectQuery, callback)
    }

    /**
     * Retrieves all records that respect an greater than filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @param callback The callback to control async request.
     * @return
     */
    override fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any, callback: GenericCallback) {
        val selectQuery = "SELECT * FROM $model where " + recordKey + ">" + recordValue
        prepareRecordQuery(model, selectQuery, callback)
    }

    /**
     * Retrieves all records that respect an lesser than filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @param callback The callback to control async request.
     * @return
     */
    override fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any, callback: GenericCallback) {
        val selectQuery = "SELECT * FROM $model where " + recordKey + "<" + recordValue
        prepareRecordQuery(model, selectQuery, callback)
    }

    /**
     * Generated generic query for a specific model.
     *
     * @param model
     * @param selectQuery
     * @param callback
     */
    private fun prepareRecordQuery(model: String, selectQuery: String, callback: GenericCallback) {

        val db = this.readableDatabase
        outputList.clear()

        val cursor: Cursor?

        try {
            cursor = db.rawQuery(selectQuery, null)

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    getRecord(cursor, model, outputList)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            db.close()
        }
        db.close()
        callback.onCallback(outputList)
    }

    /**
     * Retrieves information from query for the User model
     *
     * @param cursor
     * @param output
     */
    private fun getUserRecord(cursor: Cursor, output: ArrayList<Any>) {
        var photo = cursor.getString((cursor.getColumnIndex("photo")))
        var email = cursor.getString((cursor.getColumnIndex("email")))
        var name = cursor.getString((cursor.getColumnIndex("name")))
        var groupName = cursor.getString((cursor.getColumnIndex("groupName")))
        // TODO birthday

        output.add(User(UUID.randomUUID(), photo, email, name, null, groupName))
    }

    /**
     * Retrieves information from query for the News model
     *
     * @param cursor
     * @param output
     */
    private fun getNewsRecord(cursor: Cursor, output: ArrayList<Any>) {
        var title = cursor.getString((cursor.getColumnIndex("title")))
        var body = cursor.getString((cursor.getColumnIndex("body")))
        var details = cursor.getString((cursor.getColumnIndex("details")))
        var image = cursor.getString((cursor.getColumnIndex("image")))

        output.add(News(UUID.randomUUID(), title, body, details, image))
    }

    /**
     * Retrieves information from query for the Group model
     *
     * @param cursor
     * @param output
     */
    private fun getGroupRecord(cursor: Cursor, output: ArrayList<Any>) {
        var name = cursor.getString((cursor.getColumnIndex("name")))
        var description = cursor.getString((cursor.getColumnIndex("description")))
        var latitude = cursor.getDouble((cursor.getColumnIndex("latitude")))
        var longitude = cursor.getDouble((cursor.getColumnIndex("longitude")))

        output.add(Group(UUID.randomUUID(), name, description, latitude, longitude))
    }

    /**
     * Retrieves information from query for the Event model
     *
     * @param cursor
     * @param output
     */
    private fun getEventRecord(cursor: Cursor, output: ArrayList<Any>) {

        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "name VARCHAR(256)," +
            "description VARCHAR(256)," +
            "startDate TEXT," +
            "endDate TEXT," +
            "attachment VARCHAR(256)," +
            "shared INTEGER)"
        var name = cursor.getString((cursor.getColumnIndex("name")))
        var description = cursor.getString((cursor.getColumnIndex("description")))

        var startDate = cursor.getString((cursor.getColumnIndex("startDate")))
        var endDate = cursor.getString((cursor.getColumnIndex("endDate")))
        var attachment = cursor.getString((cursor.getColumnIndex("attachment")))
        var shared = cursor.getInt((cursor.getColumnIndex("shared")))
        var isShared = false

        var dateB = SimpleDateFormat("dd-MM-yyyy").parse(startDate)
        var dateE = SimpleDateFormat("dd-MM-yyyy").parse(endDate)

        if (shared == 1) {
            isShared = true
        }

        output.add(Event(UUID.randomUUID(), name, description, dateE, dateB, attachment, isShared))
    }

    /**
     * Retrieves information from query filtering model
     *
     * @param cursor
     * @param output
     */
    fun getRecord(cursor: Cursor, model: String, output: ArrayList<Any>) {
        if (model.equals("Users")) {
            return getUserRecord(cursor, output)
        } else if (model.equals("News")) {
            return getNewsRecord(cursor, output)
        } else if (model.equals("Events")) {
            return getEventRecord(cursor, output)
        } else {
            return getGroupRecord(cursor, output)
        }
    }
}
