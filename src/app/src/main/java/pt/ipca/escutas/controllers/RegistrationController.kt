package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.User
import pt.ipca.escutas.views.RegistrationActivity

/**
 * Defines the [RegistrationActivity] controller.
 *
 */
class RegistrationController : BaseController() {

    fun addUser(user: User) {

        mAuth.addUser(user)
    }
}
