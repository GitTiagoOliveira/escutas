package pt.ipca.escutas.services.callbacks

import pt.ipca.escutas.models.User

interface UserCallback {

    /**
     * Returns [user] of current session.
     *
     * @param user
     */
    fun onCallback(user: User)
}
