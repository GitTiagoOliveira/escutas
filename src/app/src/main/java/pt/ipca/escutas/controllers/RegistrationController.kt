package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings.MSG_STORAGE_USER_LOCATION
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
     * @param user
     * @param callback
     */
    fun addUser(user: User, callback: AuthCallback) {
        auth.addUser(user, callback)
    }


    /**
     * Upload image to the storage service.
     *
     * @param filePath
     * @param fileStream
     */
    fun uploadImage(filePath: String, fileStream : InputStream) {
        storage.createFile(filePath, fileStream)
    }

    /**
     * Save the specified user details to the database service.
     *
     * @param user
     * @param inputStream
     * @param authCallback
     */
    fun saveUser(user: User, inputStream: InputStream?, authCallback: AuthCallback) {
        if (inputStream != null && user.photo.isNotEmpty()) {
            uploadImage(user.photo,inputStream)
        }
        database.addRecord(MSG_STORAGE_USER_LOCATION, user)
    }

    /**
     * Retrieves current session user email.
     *
     * @return
     */
    fun getCustomUserEmail(): String {
        return auth.getCurrentUser().email
    }
}
