package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IDatabaseService

/**
 * Defines the exception raised by an [IDatabaseService].
 *
 */
class DatabaseException (message: String) : Exception(message)
