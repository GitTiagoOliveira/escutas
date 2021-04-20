package pt.ipca.escutas.services

import pt.ipca.escutas.services.contracts.IAuthService
import com.google.firebase.auth.FirebaseAuth
import pt.ipca.escutas.models.User

/**
 * Defines a Firebase implementation of an [IAuthService].
 *
 */
class FirebaseAuthService : IAuthService {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun addUser(user: User){
        mAuth.createUserWithEmailAndPassword(user.email.toString(), user.password)
    }

    fun loginUser(user: User){
        mAuth.signInWithEmailAndPassword(user.email.toString(), user.password)
    }

    fun resetPassword(user: User){
        mAuth.sendPasswordResetEmail(user.email.toString())
    }

    fun getCurrentUser(){
        mAuth.currentUser
    }
}
