package pt.ipca.escutas.services.contracts

import pt.ipca.escutas.services.callbacks.GenericCallback

/**
 * Defines the contract of a database service.
 *
 */
interface IDatabaseService {

    /**
     * Adds a new document [record] to a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param record The record represents the document.
     */
    fun addRecord(model: String, record: Any, param: GenericCallback)

    /**
     * Updates a record [record] associated to document [documentId] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param documentId The documentId represents the document identifier.
     * @param record The record represents the document.
     */
    fun updateRecord(model: String, documentId: String, record: Any)

    /**
     * Deletes a document [documentId] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param documentId The documentId represents the document identifier.
     */
    fun deleteRecord(model: String, documentId: String?)

    /**
     * Retrieves all records of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @return
     */
    fun getAllRecords(model: String, callback: GenericCallback)

    /**
     * Retrieves all records that respect an equal filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @return
     */
    fun getRecordWithEqualFilter(
        model: String,
        recordKey: String,
        recordValue: Any,
        firebaseDBCallback: GenericCallback
    )

    /**
     * Retrieves all records that respect an greater than filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @return
     */
    fun getRecordWithGreaterThanFilter(model: String, recordKey: String, recordValue: Any, callback: GenericCallback)

    /**
     * Retrieves all records that respect an lesser than filter based on [recordKey] and [recordValue] of a specific collection [model].
     *
     * @param model The model represents the collection.
     * @param recordKey The recordKey represents the filter column.
     * @param recordValue The recordValue represents the filter column value.
     * @return
     */
    fun getRecordWithLessThanFilter(model: String, recordKey: String, recordValue: Any, callback: GenericCallback)
}
