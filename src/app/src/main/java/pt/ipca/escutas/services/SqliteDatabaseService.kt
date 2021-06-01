package pt.ipca.escutas.services

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.News
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.contracts.IDatabaseService
import java.util.*
import kotlin.collections.HashMap

/**
 * Defines a SQLite implementation of an [IDatabaseService].
 *
 */
class SqliteDatabaseService(context: Context) : IDatabaseService,  SQLiteOpenHelper(context, "EscutasDB", null, 1) {

    // this is called the first time a database is accessed.
    override fun onCreate(db: SQLiteDatabase?) {

        //Create User Table
        val createUserTable = "CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "photo VARCHAR(256),"+
                "email VARCHAR(256),"+
                "birthday VARCHAR(256),"+
                "name VARCHAR(256),"+
                "groupName VARCHAR(256))";

        //Create User Table
        val createNewsTable = "CREATE TABLE News (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "title VARCHAR(256),"+
                "body VARCHAR(256),"+
                "image VARCHAR(256))";

        //Create Group Table
        val createGroupTable = "CREATE TABLE Group (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "name VARCHAR(256),"+
                "description VARCHAR(256),"+
                "description REAL,"+
                "latitude REAL)";

        db?.execSQL(createUserTable)
        db?.execSQL(createNewsTable)
        db?.execSQL(createGroupTable)
    }

    // this is called if the database version number changes.
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    override fun addRecord(model: String, record: Any, param: FirebaseDBCallback) {
        val db = this.writableDatabase
        var result = db.insert(model, null, record as ContentValues?)
        var resultList : HashMap<String, Any> = HashMap<String, Any> ()
        resultList["result"] = result
        param.onCallback(resultList)
    }

    override fun updateRecord(model: String, whereClause: String, record: Any) {
        val db = this.readableDatabase
        val success = db.update(model, record as ContentValues?, whereClause, null)
        db.close()
    }

    override fun deleteRecord(model: String, whereClause: String) {
        val db = this.readableDatabase
        val success = db.delete(model, whereClause, null)
        db.close()
    }

    override fun getAllRecords(model: String, callback: FirebaseDBCallback) {
        val selectQuery = "SELECT * FROM $model"
        val db = this.readableDatabase
        val output = HashMap<String, Any>()
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)

            if(cursor != null && cursor.moveToFirst()){
                do{
                    getRecord(cursor, model, output)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

        callback.onCallback(output)
    }

    override fun getRecordWithEqualFilter(
        model: String,
        recordKey: String,
        recordValue: Any,
        callback: FirebaseDBCallback
    ) {
        val selectQuery = "SELECT * FROM $model where " + recordKey + "=" + recordValue;
        prepareRecordQuery(model, selectQuery, callback)
    }

    override fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any, callback: FirebaseDBCallback){
        val selectQuery = "SELECT * FROM $model where " + recordKey + ">" + recordValue;
        prepareRecordQuery(model, selectQuery, callback)
    }

    override fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any, callback: FirebaseDBCallback) {
        val selectQuery = "SELECT * FROM $model where " + recordKey + "<" + recordValue;
        prepareRecordQuery(model, selectQuery, callback)
    }

    fun prepareRecordQuery(model: String, selectQuery: String, callback: FirebaseDBCallback){

        val db = this.readableDatabase
        val output = HashMap<String, Any>()
        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)

            if(cursor != null && cursor.moveToFirst()){
                do{
                    getRecord(cursor, model, output)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

        callback.onCallback(output)
    }

    fun getUserRecord(cursor: Cursor, output: HashMap<String, Any>) {
        var photo = cursor.getString((cursor.getColumnIndex("photo")))
        var email = cursor.getString((cursor.getColumnIndex("email")))
        var name = cursor.getString((cursor.getColumnIndex("name")))
        var groupName = cursor.getString((cursor.getColumnIndex("groupName")))
        //TODO birthday

        output.put(email,User(UUID.randomUUID(), photo, email, name, null, groupName));
    }

    fun getNewsRecord(cursor: Cursor, output: HashMap<String, Any>) {
        var title = cursor.getString((cursor.getColumnIndex("title")))
        var body = cursor.getString((cursor.getColumnIndex("body")))
        var image = cursor.getString((cursor.getColumnIndex("image")))

        output.put(UUID.randomUUID().toString(), News(UUID.randomUUID(), title, body, image))
    }

    fun getGroupRecord(cursor: Cursor, output: HashMap<String, Any>) {
        var name = cursor.getString((cursor.getColumnIndex("name")))
        var description = cursor.getString((cursor.getColumnIndex("description")))
        var latitude = cursor.getDouble((cursor.getColumnIndex("latitude")))
        var longitude = cursor.getDouble((cursor.getColumnIndex("longitude")))

        output.put(UUID.randomUUID().toString(), Group(UUID.randomUUID(), name, description, latitude, longitude))
    }

    fun getRecord(cursor: Cursor, model: String, output: HashMap<String, Any>) {
        if (model.equals("Users")){
            return getUserRecord(cursor, output)
        } else if(model.equals("News")){
            return getNewsRecord(cursor, output)
        } else {
            return getGroupRecord(cursor, output)
        }
    }
}
