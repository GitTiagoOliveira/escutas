package pt.ipca.escutas.controllers

import com.google.firebase.Timestamp
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.callbacks.StorageCallback
import pt.ipca.escutas.services.callbacks.UserCallback
import pt.ipca.escutas.views.fragments.ProfileFragment
import java.util.*

/**
 * Defines the [ProfileFragment] controller.
 *
 */
class ProfileController : BaseController() {

    /**
     * Internal group list cache.
     */
    private var user: User? = null

    /**
     * Retrieves current session user.
     *
     * @param callback
     */
    fun getUser(callback: UserCallback) {
        if(user != null){
            callback.onCallback(user!!)
        } else {
            getUserDetails(object : FirebaseDBCallback {
                override fun onCallback(list: HashMap<String, Any>) {

                    list.forEach { (key, value) ->
                        val values = value as HashMap<String, Any>
                        user = User(
                            UUID.randomUUID(),
                            values["photo"] as String,
                            values["email"] as String,
                            values["name"] as String,
                            values["password"] as String,
                            (values["birthday"] as Timestamp).toDate(),
                            values["groupName"] as String,
                        )
                        callback.onCallback(user!!)
                    }
                }
            })
        }
    }

    /**
     * Retrieves current session user details if not in cache.
     *
     * @param firebaseDBCallback
     */
    private fun getUserDetails(firebaseDBCallback: FirebaseDBCallback){
        database.getRecordWithEqualFilter(Strings.MSG_STORAGE_USER_LOCATION, "email" , auth.getCurrentUser().email, firebaseDBCallback)
    }

    /**
     * Retrieves current session user profile image.
     *
     * @param imagePath
     * @param callback
     */
    fun getUserImage(imagePath: String, callback: StorageCallback) {
        storage.readFile(imagePath, callback)
    }

}
