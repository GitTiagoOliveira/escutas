package pt.ipca.escutas.controllers

import com.google.firebase.auth.AuthCredential
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.views.LoginActivity

/**
 * Defines the [LoginActivity] controller.
 *
 */
class LoginController : BaseController() {
    /**
     * Logs-in the user through the specified credentials.
     *
     * @param email The user email.
     * @param password The user password.
     */
    fun loginUser(email: String, password: String, callback: AuthCallback) {
        auth.loginUser(email, password, callback)
    }

    fun loginUserWithCredential(credential: AuthCredential) {
        auth.loginUserWithCredential(credential)
    }
}
