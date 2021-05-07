package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.User
import pt.ipca.escutas.views.RegistrationActivity

/**
 * Defines the [RegistrationActivity] controller.
 *
 */
class RegistrationController : BaseController() {
    /**
     * Adds the specified user to the authentication service.
     *
     * @param user The user.
     */
    fun addUser(user: User) {
        auth.addUser(user)
    }
}
