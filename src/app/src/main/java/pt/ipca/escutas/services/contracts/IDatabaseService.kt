package pt.ipca.escutas.services.contracts

/**
 * Defines the contract of a database service.
 *
 */
interface IDatabaseService {

    /**
     * Adds a new document [record] to a specific collection [model]
     *
     * @param model
     * @param record
     */
    fun addRecord(model: String, record: Any)

    /**
     * Updates a record [record] associated to document [documentId] of a specific collection [model]
     *
     * @param model
     * @param documentId
     * @param record
     */
    fun updateRecord(model: String, documentId: String, record: Any)

    /**
     * Deletes a document [documentId] of a specific collection [model]
     *
     * @param model
     * @param documentId
     */
    fun deleteRecord(model: String, documentId: String)

    /**
     * Retrieves all records of a specific collection [model]
     *
     * @param model
     * @return
     */
    fun getAllRecords(model: String): HashMap<String, Any>

    /**
     * Retrieves all records that respect an equal filter based on [recordKey] and [recordValue] of a specific collection [model]
     *
     * @param model
     * @param recordKey
     * @param recordValue
     * @return
     */
    fun getRecordWithEqualFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any>

    /**
     * Retrieves all records that respect an greater than filter based on [recordKey] and [recordValue] of a specific collection [model]
     *
     * @param model
     * @param recordKey
     * @param recordValue
     * @return
     */
    fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any>

    /**
     * Retrieves all records that respect an lesser than filter based on [recordKey] and [recordValue] of a specific collection [model]
     *
     * @param model
     * @param recordKey
     * @param recordValue
     * @return
     */
    fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any): HashMap<String, Any>
}
