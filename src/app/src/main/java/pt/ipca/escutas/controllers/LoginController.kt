package pt.ipca.escutas.controllers

import pt.ipca.escutas.views.LoginActivity

/**
 * Defines the [LoginActivity] controller.
 *
 */
class LoginController : BaseController() {

    fun loginUser(email: String, password: String) {

        // TODO - Adicionar encriptção
        auth.loginUser(email, password)
    }
}
