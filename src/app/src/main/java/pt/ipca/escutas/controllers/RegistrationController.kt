package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.views.RegistrationActivity
import java.io.InputStream

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
    fun addUser(user: User, callback: AuthCallback) {
        auth.addUser(user, callback)
    }


    fun uploadImage(filePath: String, fileStream : InputStream) {
        storage.createFile(filePath, fileStream)
    }

    fun saveUser(user: User, inputStream: InputStream?, authCallback: AuthCallback) {
        if (inputStream != null && user.photo.isNotEmpty()) {
            uploadImage(user.photo,inputStream)
        }
        database.addRecord("users", user)
    }

    fun getCustomUserEmail(): String {
        return auth.getCurrentUser().email
    }
}
