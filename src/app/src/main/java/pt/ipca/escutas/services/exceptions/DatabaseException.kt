package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IDatabaseService

/**
 * Defines the exception raised by an [IDatabaseService].
 *
 */
class DatabaseException
/**
 * Creates a database service exception. A [message] explaining the error must be provided.
 */
    (message: String) : Exception(message)
