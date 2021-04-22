package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IStorageService

/**
 * Defines the exception raised by an [IStorageService].
 *
 */
class StorageException
/**
 * Creates a storage service exception. A [message] explaining the error must be provided.
 */
    (message: String) : Exception(message)
