package pt.ipca.escutas.services

import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.contracts.IDatabaseService

/**
 * Defines a SQLite implementation of an [IDatabaseService].
 *
 */
class SqliteDatabaseService : IDatabaseService {
    override fun addRecord(model: String, record: Any) {
        TODO("Not yet implemented")
    }

    override fun updateRecord(model: String, documentId: String, record: Any) {
        TODO("Not yet implemented")
    }

    override fun deleteRecord(model: String, documentId: String) {
        TODO("Not yet implemented")
    }

    override fun getAllRecords(model: String, callback: FirebaseDBCallback) {
        TODO("Not yet implemented")
    }

    override fun getRecordWithEqualFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any> {
        TODO("Not yet implemented")
    }
}
