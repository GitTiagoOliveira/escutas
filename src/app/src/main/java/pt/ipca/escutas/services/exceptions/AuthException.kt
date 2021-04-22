package pt.ipca.escutas.services.exceptions

import pt.ipca.escutas.services.contracts.IAuthService

/**
 * Defines the exception raised by an [IAuthService].
 *
 */
class AuthException (message: String) : Exception(message)
