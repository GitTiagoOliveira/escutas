package pt.ipca.escutas.controllers

import android.graphics.Bitmap
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Timestamp
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.ProfileActivity
import java.lang.Exception
import java.util.UUID

/**
 * Defines the [ProfileController] controller.
 *
 */
class ProfileController : BaseController() {

    /**
     * Internal user cache.
     */
    private var user: User? = null

    /**
     * Internal profile user image cache.
     */
    private var profileImage: Bitmap? = null

    /**
     * Retrieves current session user.
     *
     * @param callback
     */
    fun getUser(callback: GenericCallback) {
            getUserDetails(object : GenericCallback {
                override fun onCallback(value: Any?) {
                    var list = value as HashMap<String, Any>
                    list.forEach { (key, value) ->
                        val values = value as HashMap<String, Any>
                        val idMap = value["id"] as HashMap<String, Long>
                        user = User(
                            UUID(idMap.get("mostSignificantBits")!!, idMap.get("leastSignificantBits")!!),
                            values["photo"] as String,
                            values["email"] as String,
                            values["name"] as String,
                            (values["birthday"] as Timestamp).toDate(),
                            values["groupName"] as String,
                        )
                        callback.onCallback(user!!)
                    }
                }
            })
    }

    /**
     * Retrieves current session user details if not in cache.
     *
     * @param firebaseDBCallback
     */
    private fun getUserDetails(callback: GenericCallback) {
        database.getRecordWithEqualFilter(Strings.MSG_STORAGE_USER_LOCATION, "email", auth.getCurrentUser().email, callback)
    }

    /**
     * Retrieves current session user profile image.
     *
     * @param imagePath
     * @param callback
     */
    fun getUserImage(imagePath: String, callback: GenericCallback) {
        storage.readFile(imagePath, callback)
    }

    /**
     * Saves profile image in cache
     *
     * @param image
     */
    fun saveImage(image: Bitmap) {
        profileImage = image
    }

    /**
     * Logout user
     *
     * @param activity
     */
    fun logoutUser(activity: ProfileActivity) {

        for (userDetails in auth.getCurrentUser().providerData) {
            if (userDetails.providerId == "google.com") {
                gmailLogout(activity)
            } else if (userDetails.providerId == "facebook.com") {
                facebookLogout(activity)
            }
        }
        // Firebase Logout
        auth.logout()
    }

    /**
     * Logout gmail user
     *
     * @param activity
     */
    private fun gmailLogout(activity: ProfileActivity) {
        try {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
            val googleSignInClient = GoogleSignIn.getClient(activity, gso)
            googleSignInClient.signOut()
        } catch (e: Exception) {
            // Ignore
        }
    }

    /**
     * Logout facebook user
     *
     * @param activity
     */
    private fun facebookLogout(activity: ProfileActivity) {
        try {
            LoginManager.getInstance().logOut()
        } catch (e: Exception) {
            // Ignore
        }
    }
}
