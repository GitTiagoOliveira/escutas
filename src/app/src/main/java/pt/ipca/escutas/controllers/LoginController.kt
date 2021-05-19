package pt.ipca.escutas.controllers

import com.google.firebase.auth.AuthCredential
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
    fun loginUser(email: String, password: String) {
        auth.loginUser(email, password)
    }

    fun loginUserWithCredential(credential: AuthCredential) {
        auth.loginUserWithCredential(credential)
    }
}
