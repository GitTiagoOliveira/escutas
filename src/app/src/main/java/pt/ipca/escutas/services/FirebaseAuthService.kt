package pt.ipca.escutas.services

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.contracts.IAuthService
import pt.ipca.escutas.services.exceptions.AuthException

/**
 * Defines a Firebase implementation of an [IAuthService].
 *
 */
class FirebaseAuthService : IAuthService {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun getCurrentUser(): FirebaseUser {
        val user = mAuth.currentUser

        if (user != null) {
            return user
        } else {
            throw AuthException(Strings.MSG_NO_USER_AVAILABLE)
        }
    }

    override fun addUser(user: User) {

        mAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_USER_CREATED)
                } else {
                    Log.w(TAG, Strings.MSG_FAILED_USER_CREATE, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_CREATE)
                }
            }
    }

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

    override fun updateUserEmail(user: User) {
        val firebaseUser = getCurrentUser()

        firebaseUser.updateEmail(user.email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_USER_EMAIL_UPDATE)
                } else {
                    Log.w(TAG, Strings.MSG_FAILED_USER_EMAIL_UPDATE, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_EMAIL_UPDATE)
                }
            }
    }

    override fun updateUserPassword(user: User) {
        val firebaseUser = getCurrentUser()

        firebaseUser.updatePassword(user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_USER_PASSWORD_UPDATE)
                } else {
                    Log.w(TAG, Strings.MSG_FAILED_USER_PASSWORD_UPDATE, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAILED_USER_PASSWORD_UPDATE)
                }
            }
    }

    override fun sendPasswordResetEmail(user: User) {

        mAuth.sendPasswordResetEmail(user.email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, Strings.MSG_EMAIL_SENT)
                } else {
                    Log.w(TAG, Strings.MSG_FAIL_EMAIL, task.exception)
                    throw AuthException(task.exception?.message ?: Strings.MSG_FAIL_EMAIL)
                }
            }
    }

    override fun loginUser(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, Strings.MSG_USER_LOGIN)
            } else {
                Log.w(TAG, Strings.MSG_FAIL_USER_LOGIN, task.exception)
                throw AuthException(task.exception?.message ?: Strings.MSG_FAIL_USER_LOGIN)
            }
        }
    }

    override fun loginUserWithCredential(credential: AuthCredential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                getCurrentUser()
                Log.d(TAG, Strings.MSG_USER_LOGIN)
            } else {
                Log.w(TAG, Strings.MSG_FAIL_USER_LOGIN, task.exception)
                throw AuthException(task.exception?.message ?: Strings.MSG_FAIL_USER_LOGIN)
            }
        }
    }

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
