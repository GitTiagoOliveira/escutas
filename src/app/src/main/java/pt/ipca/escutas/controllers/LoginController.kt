package pt.ipca.escutas.controllers

import com.google.firebase.auth.AuthCredential
import pt.ipca.escutas.resources.Strings.MSG_STORAGE_USER_LOCATION
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.LoginActivity

/**
 * Defines the [LoginActivity] controller.
 *
 */
class LoginController : BaseController() {

    /**
     * Logs-in the user through the specified credentials.
     *
     * @param email
     * @param password
     * @param callback
     */
    fun loginUser(email: String, password: String, callback: AuthCallback) {
        auth.loginUser(email, password, callback)
    }

    /**
     * Logs-in the user through the specified credentials.
     *
     * @param credential
     */
    fun loginUserWithCredential(credential: AuthCredential, callback: AuthCallback) {
        auth.loginUserWithCredential(credential, callback)
    }

    /**
     * Validates if a specific user already exists.
     *
     * @param firebaseDBCallback
     */
    fun userExists(callback: GenericCallback) {
        database.getRecordWithEqualFilter(MSG_STORAGE_USER_LOCATION, "email", auth.getCurrentUser().email, callback)
    }

    /**
     * Sends a reset password email for a specific user email.
     *
     * @param email
     * @param callback
     */
    fun resetPassword(email: String, callback: AuthCallback) {
        auth.sendPasswordResetEmail(email, callback)
    }
}
