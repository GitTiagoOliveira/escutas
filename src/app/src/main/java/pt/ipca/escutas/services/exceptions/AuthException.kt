package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IAuthService

/**
 * Defines the exception raised by an [IAuthService].
 *
 */
class AuthException
/**
 * Creates a authentication service exception. A [message] explaining the error must be provided.
 */
    (message: String) : Exception(message)
