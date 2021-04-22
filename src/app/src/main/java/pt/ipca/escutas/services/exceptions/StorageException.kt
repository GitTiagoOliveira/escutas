package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IStorageService

/**
 * Defines the exception raised by an [IStorageService].
 *
 */
class StorageException (message: String) : Exception(message)
