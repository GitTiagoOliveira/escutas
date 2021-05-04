package pt.ipca.escutas.services

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.ipca.escutas.R
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.contracts.IAuthService
import pt.ipca.escutas.services.exceptions.AuthException

/**
 * Defines a Firebase implementation of an [IAuthService].
 *
 */
class FirebaseAuthService : IAuthService {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val resources: Resources = Resources.getSystem()

    private fun getCurrentUser(): FirebaseUser? {

        val user = mAuth.currentUser
        if (user != null) {
            return user
        } else {
            throw AuthException(resources.getString(R.string.msg_no_user_available))
        }
    }

    override fun addUser(user: User) {

        mAuth.createUserWithEmailAndPassword(user.email.toString(), user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, resources.getString(R.string.msg_user_created))
                } else {
                    Log.w(TAG, resources.getString(R.string.msg_failed_user_create), task.exception)
                    throw AuthException(task.exception?.message ?: resources.getString(R.string.msg_failed_user_create))
                }
            }
    }

    override fun deleteUser() {
        val user = getCurrentUser()

        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, resources.getString(R.string.msg_user_deleted))
            } else {
                Log.w(TAG, resources.getString(R.string.msg_failed_user_delete), task.exception)
                throw AuthException(task.exception?.message ?: resources.getString(R.string.msg_failed_user_delete))
            }
        }
    }

    override fun updateUserEmail(user: User) {
        val firebase_user = getCurrentUser()

        firebase_user!!.updateEmail(user.email.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, resources.getString(R.string.msg_user_email_update))
                } else {
                    Log.w(TAG, resources.getString(R.string.msg_failed_user_email_update), task.exception)
                    throw AuthException(task.exception?.message ?: resources.getString(R.string.msg_failed_user_email_update))
                }
            }
    }

    override fun updateUserPassword(user: User) {
        val firebase_user = getCurrentUser()

        firebase_user!!.updatePassword(user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, resources.getString(R.string.msg_user_password_update))
                } else {
                    Log.w(TAG, resources.getString(R.string.msg_failed_user_password_update), task.exception)
                    throw AuthException(task.exception?.message ?: resources.getString(R.string.msg_failed_user_password_update))
                }
            }
    }

    override fun sendPasswordResetEmail(user: User) {

        mAuth.sendPasswordResetEmail(user.email.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, resources.getString(R.string.msg_email_sent))
                } else {
                    Log.w(TAG, resources.getString(R.string.msg_fail_email), task.exception)
                    throw AuthException(task.exception?.message ?: resources.getString(R.string.msg_fail_email))
                }
            }
    }

    override fun loginUser(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, resources.getString(R.string.msg_user_login))
            } else {
                Log.w(TAG, resources.getString(R.string.msg_fail_user_login), task.exception)
                throw AuthException(task.exception?.message ?: resources.getString(R.string.msg_fail_user_login))
            }
        }
    }

    override fun getCurrentUserDetails() {

        val user = getCurrentUser()

        user?.let {
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

    override fun getCurrentUserDetailsViaProvider() {

        val user = getCurrentUser()

        user?.let {
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
