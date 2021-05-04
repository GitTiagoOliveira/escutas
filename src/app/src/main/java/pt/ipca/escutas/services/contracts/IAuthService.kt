package pt.ipca.escutas.services.contracts

import pt.ipca.escutas.models.User

/**
 * Defines the contract of an authentication service.
 *
 */
interface IAuthService {

    /**
     * Adds a new user via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    fun addUser(user: User)

    /**
     * Deletes current user via authentication service
     *
     */
    fun deleteUser()

    /**
     * Updated user email via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    fun updateUserEmail(user: User)

    /**
     * Updated user password via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    fun updateUserPassword(user: User)

    /**
     * Sends user email to reset user password via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    fun sendPasswordResetEmail(user: User)

    /**
     * Login user via authentication service based on the provided [email] and [password].
     *
     * @param email The user email.
     * @param password The user password.
     */
    fun loginUser(email: String, password: String)

    /**
     * Retrieves current user details such as email, photo url.
     *
     */
    fun getCurrentUserDetails()

    /**
     * Retrieves current user details such as email, photo url via provider (example: Google, Facebook)
     *
     */
    fun getCurrentUserDetailsViaProvider()
}
