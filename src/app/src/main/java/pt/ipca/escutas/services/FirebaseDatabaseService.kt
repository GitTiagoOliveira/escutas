package pt.ipca.escutas.services

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.contracts.IDatabaseService
import pt.ipca.escutas.services.exceptions.DatabaseException

/**
 * Defines a Firebase implementation of an [IDatabaseService].
 *
 */
class FirebaseDatabaseService : IDatabaseService {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    /**
     * Adds a new document [record] to a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param record The record represents the document.
     */
    override fun addRecord(model: String, record: Any, callback: FirebaseDBCallback) {

        val modelData = this.db.collection(model)

        modelData.add(record).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_ADD, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_ADD)
            } else {
                callback.onCallback(hashMapOf())
            }
        }
    }

    /**
     * Updates a record [record] associated to document [documentId] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param documentId The documentId represents the document identifier.
     * @param record The record represents the document.
     */
    override fun updateRecord(model: String, documentId: String, record: Any) {

        val modelData = this.db.collection(model)

        modelData.document(documentId).set(record).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_UPDATE, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_UPDATE)
            }
        }
    }

    /**
     * Deletes a document [documentId] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param documentId The documentId represents the document identifier.
     */
    override fun deleteRecord(model: String, documentId: String) {

        val modelData = this.db.collection(model)

        modelData.document(documentId).delete().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_REMOVE, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_REMOVE)
            }
        }
    }

    /**
     * Retrieves all records of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @return
     */
    override fun getAllRecords(model: String, firebaseCallback: FirebaseDBCallback) {

        val modelData = this.db.collection(model)
        val output = HashMap<String, Any>()

        modelData.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
                firebaseCallback.onCallback(output)
            } else {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_GET)
            }
        }
    }

    /**
     * Retrieves all records that respect an equal filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @return
     */
    override fun getRecordWithEqualFilter(
        model: String,
        recordKey: String,
        recordValue: Any,
        firebaseDBCallback: FirebaseDBCallback
    ){

        val modelData = this.db.collection(model)
        val output = HashMap<String, Any>()

        modelData.whereEqualTo(recordKey, recordValue).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
                firebaseDBCallback.onCallback(output)
            } else {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_GET)
            }
        }
    }

    /**
     * Retrieves all records that respect an greater than filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @return
     */
    override fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any, callback: FirebaseDBCallback) {

        val modelData = this.db.collection(model)
        val output = HashMap<String, Any>()

        modelData.whereGreaterThan(recordKey, recordValue).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
            } else {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_GET)
            }
        }
    }

    /**
     * Retrieves all records that respect an lesser than filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @return
     */
    override fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any, callback: FirebaseDBCallback){

        val modelData = this.db.collection(model)
        val output = HashMap<String, Any>()

        modelData.whereLessThan(recordKey, recordValue).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
            } else {
                Log.w(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
                throw DatabaseException(task.exception?.message ?: Strings.MSG_FAIL_DATABASE_GET)
            }
        }
    }
}
