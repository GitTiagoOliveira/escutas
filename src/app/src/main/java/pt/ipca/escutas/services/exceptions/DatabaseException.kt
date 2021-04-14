package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IDatabaseService

/**
 * Defines the exception raised by an [IDatabaseService].
 *
 */
class DatabaseException : Exception {
    /**
     * Creates a database service exception. A [message] explaining the error must be provided.
     */
    constructor(message: String) : super(message)
}
