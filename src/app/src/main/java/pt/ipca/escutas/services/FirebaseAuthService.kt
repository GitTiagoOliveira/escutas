package pt.ipca.escutas.services

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.services.contracts.IAuthService
import pt.ipca.escutas.services.exceptions.AuthException

/**
 * Defines a Firebase implementation of an [IAuthService].
 *
 */
class FirebaseAuthService : IAuthService {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Returns current user.
     *
     * @return
     */
    fun getCurrentUser(): FirebaseUser {
        val user = mAuth.currentUser

        if (user != null) {
            return user
        } else {
            throw AuthException(Strings.MSG_NO_USER_AVAILABLE)
        }
    }

    /**
     * Adds a new user via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    override fun addUser(email: String, password: String, callback: AuthCallback) {

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_USER_CREATED)
                    callback.onCallback()
                } else {
                    Log.w(TAG, Strings.MSG_FAILED_USER_CREATE, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_CREATE)
                }
            }
    }

    /**
     * Deletes current user via authentication service.
     *
     */
    override fun deleteUser() {
        val user = getCurrentUser()

        user.delete().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, Strings.MSG_USER_DELETED)
            } else {
                Log.w(TAG, Strings.MSG_FAILED_USER_DELETE, task.exception)
                throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_DELETE)
            }
        }
    }

    /**
     * Updated user email via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    override fun updateUserEmail(user: User, callback: AuthCallback) {
        val firebaseUser = getCurrentUser()

        firebaseUser.updateEmail(user.email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_USER_EMAIL_UPDATE)
                    callback.onCallback()
                } else {
                    Log.w(TAG, Strings.MSG_FAILED_USER_EMAIL_UPDATE, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_EMAIL_UPDATE)
                }
            }
    }

    /**
     * Updated user password via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    override fun updateUserPassword(password: String, callback: AuthCallback) {
        val firebaseUser = getCurrentUser()

        firebaseUser.updatePassword(password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_USER_PASSWORD_UPDATE)
                    callback.onCallback()
                } else {
                    Log.w(TAG, Strings.MSG_FAILED_USER_PASSWORD_UPDATE, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_PASSWORD_UPDATE)
                }
            }
    }

    /**
     * Sends user email to reset user password via authentication service based on the details available in [user].
     *
     * @param user The user object contains all necessary data as email and password.
     */
    override fun sendPasswordResetEmail(email: String, callback: AuthCallback) {

        mAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_EMAIL_SENT)
                    callback.onCallback()
                } else {
                    Log.w(TAG, Strings.MSG_FAIL_EMAIL, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAIL_EMAIL)
                }
            }
    }

    /**
     * Login user via authentication service based on the provided [email] and [password].
     *
     * @param email The user email.
     * @param password The user password.
     */
    override fun loginUser(email: String, password: String, callback: AuthCallback) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, Strings.MSG_USER_LOGIN)
                callback.onCallback()
            } else {
                Log.w(TAG, Strings.MSG_FAIL_USER_LOGIN, task.exception)
                callback.onCallbackError(task.exception?.message ?: Strings.MSG_FAIL_USER_LOGIN)
            }
        }
    }

    /**
     * Create auth token based on [credential].
     *
     * @param credential
     */
    override fun loginUserWithCredential(credential: AuthCredential, callback: AuthCallback) {

        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, Strings.MSG_USER_LOGIN)
                callback.onCallback()
            } else {
                Log.w(TAG, Strings.MSG_FAIL_USER_LOGIN, task.exception)
                callback.onCallbackError(task.exception?.message ?: Strings.MSG_FAIL_USER_LOGIN)
            }
        }
    }

    /**
     * Delete auth token for current session.
     *
     */
    override fun logout() {

        mAuth.signOut()
    }

    /**
     * Retrieves current user details such as email, photo url.
     *
     */
    override fun getCurrentUserDetails() {
        val user = getCurrentUser()

        user.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid
        }
    }

    /**
     * Retrieve current session provider user details.
     *
     */
    override fun getCurrentUserDetailsViaProvider() {
        val user = getCurrentUser()

        user.let {
            for (profile in it.providerData) {
                // Id of the provider (ex: google.com)
                val providerId = profile.providerId

                // UID specific to the provider
                val uid = profile.uid

                // Name, email address, and profile photo Url
                val name = profile.displayName
                val email = profile.email
                val photoUrl = profile.photoUrl
            }
        }
    }
}
