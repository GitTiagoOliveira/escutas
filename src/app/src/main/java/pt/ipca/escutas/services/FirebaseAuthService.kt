package pt.ipca.escutas.services

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.contracts.IAuthService
import pt.ipca.escutas.services.exceptions.AuthException

/**
 * Defines a Firebase implementation of an [IAuthService].
 *
 */
class FirebaseAuthService : IAuthService {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private fun getCurrentUser(): FirebaseUser? {

        val user = mAuth.currentUser
        if (user != null) {
            return user
        } else {
            throw AuthException("No User Available")
        }
    }

    override fun addUser(user: User) {

        mAuth.createUserWithEmailAndPassword(user.email.toString(), user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User created accordingly.")
                } else {
                    Log.w(TAG, "Failure creating user.", task.exception)
                    throw AuthException(task.exception?.message ?: "Failure creating user.")
                }
            }
    }

    override fun deleteUser() {
        val user = getCurrentUser()

        user?.delete()?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User account deleted.")
            } else {
                Log.w(TAG, "Failure deleting user.", task.exception)
                throw AuthException(task.exception?.message ?: "Failure deleting user.")
            }
        }
    }

    override fun updateUserEmail(user: User) {
        val firebase_user = getCurrentUser()

        firebase_user!!.updateEmail(user.email.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User email address updated.")
                } else {
                    Log.w(TAG, "Failure updating email address.", task.exception)
                    throw AuthException(task.exception?.message ?: "Failure updating email address.")
                }
            }
    }

    override fun updateUserPassword(user: User) {
        val firebase_user = getCurrentUser()

        firebase_user!!.updatePassword(user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User password updated.")
                } else {
                    Log.w(TAG, "Failure updating password.", task.exception)
                    throw AuthException(task.exception?.message ?: "Failure updating password.")
                }
            }
    }

    override fun sendPasswordResetEmail(user: User) {

        mAuth.sendPasswordResetEmail(user.email.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                } else {
                    Log.w(TAG, "Failure sending email.", task.exception)
                    throw AuthException(task.exception?.message ?: "Failure sending email.")
                }
            }
    }

    override fun loginUser(email: String, password: String) {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "User login.")
            } else {
                Log.w(TAG, "Unable to login user.", task.exception)
                throw AuthException(task.exception?.message ?: "Unable to login user.")
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
