package pt.ipca.escutas.services

import android.content.ContentValues
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.contracts.IDatabaseService

/**
 * Defines a Firebase implementation of an [IDatabaseService].
 *
 */
class FirebaseDatabaseService : IDatabaseService {

    private val storage: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun addRecord(model: String, record: Any) {

        val modelData = this.storage.collection(model)

        modelData.add(record).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_ADD, task.exception)
            }
        }
    }

    override fun updateRecord(model: String, documentId: String, record: Any) {

        val modelData = this.storage.collection(model)

        modelData.document(documentId).set(record).addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_UPDATE, task.exception)
            }
        }
    }

    override fun deleteRecord(model: String, documentId: String) {

        val modelData = this.storage.collection(model)

        modelData.document(documentId).delete().addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_REMOVE, task.exception)
            }
        }
    }

    override fun getAllRecords(model: String): HashMap<String, Any> {

        val modelData = this.storage.collection(model)
        val output = HashMap<String, Any>()

        modelData.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
            } else {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
            }
        }

        return output
    }

    override fun getRecordWithEqualFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any> {

        val modelData = this.storage.collection(model)
        val output = HashMap<String, Any>()

        modelData.whereEqualTo(recordKey, recordValue).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
            } else {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
            }
        }

        return output
    }

    override fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any> {

        val modelData = this.storage.collection(model)
        val output = HashMap<String, Any>()

        modelData.whereGreaterThan(recordKey, recordValue).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
            } else {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
            }
        }

        return output
    }

    override fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any> {

        val modelData = this.storage.collection(model)
        val output = HashMap<String, Any>()

        modelData.whereLessThan(recordKey, recordValue).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                for (document in task.result!!) {
                    output[document.id] = document.data
                }
            } else {
                Log.d(ContentValues.TAG, Strings.MSG_FAIL_DATABASE_GET, task.exception)
            }
        }

        return output
    }
}
