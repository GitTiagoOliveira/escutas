package pt.ipca.escutas.views

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.view.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.LoginController
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.exceptions.AuthException
import pt.ipca.escutas.utils.StringUtils.isValidEmail
import java.util.*
import kotlin.collections.HashMap

/**
 * Defines the login activity.
 *
 */
class LoginActivity : AppCompatActivity() {

    /**
     * The login controller.
     */
    private val loginController by lazy { LoginController() }

    /**
     * The facebook callback controller.
     */
    private val callbackManager = CallbackManager.Factory.create()

    /**
     * The gmail login number representation.
     */
    private val RC_SIGN_IN = 123

    /**
     * boolean flag to distinguish facebook login from gmail.
     */
    private var facebookRequest = true

    /**
     * The number representation for android external access request.
     */
    private val REQUEST_EXTERNAL_STORAGE = 1

    /**
     * The required permissions to upload image from external storage.
     */
    private val PERMISSIONS_STORAGE = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginView = findViewById<Button>(R.id.Button_login)
        val aboutView = findViewById<TextView>(R.id.sobre)
        val registerView = findViewById<TextView>(R.id.Button_Register)
        val gmailView = findViewById<SignInButton>(R.id.gmail_login_button)
        val facebookView = findViewById<View>(R.id.facebook_login_button) as LoginButton
        val forgotView = findViewById<TextView>(R.id.forgotPassword)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        gmailView.setOnClickListener {
            facebookRequest = false
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }

        facebookView.setOnClickListener {
            facebookRequest = true
        }
        facebookView.setReadPermissions("public_profile email")
        facebookView.registerCallback(
            callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    var credential = FacebookAuthProvider.getCredential(loginResult?.accessToken?.getToken())
                    loginController.loginUserWithCredential(credential, object : AuthCallback{
                        override fun onCallback() {
                            loginController.userExists(object : FirebaseDBCallback {
                                override fun onCallback(list: HashMap<String, Any>) {
                                    if(list.isEmpty()){
                                        verifyStoragePermissions(this@LoginActivity)
                                        val intent = Intent(this@LoginActivity, CustomRegistrationActivity::class.java)
                                        startActivity(intent)
                                    } else {
                                        val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                                        startActivity(intent)
                                    }
                                }

                            })
                        }

                        override fun onCallbackError(error: String) {
                            // Do Nothing
                        }

                    })
                }

                override fun onCancel() {
                    // Do Nothing
                }

                override fun onError(exception: FacebookException) {
                    Log.w(ContentValues.TAG, Strings.MSG_FAIL_USER_LOGIN, exception.cause)
                    throw AuthException(exception?.message ?: Strings.MSG_FAIL_USER_LOGIN)
                }
            }
        )

        loginView.setOnClickListener {
            val emailField = findViewById<EditText>(R.id.editText_login_email)
            val email = emailField.text.toString().trim()

            if (email.isEmpty()) {
                emailField.error = Strings.MSG_FIELD_BLANK
                return@setOnClickListener
            }

            if (!email.isValidEmail()) {
                emailField.error = Strings.MSG_INCORRECT_EMAIL
                return@setOnClickListener
            }

            val passwordField = findViewById<EditText>(R.id.editText_login_password)
            val password = passwordField.text.toString().trim()

            if (password.isEmpty()) {
                passwordField.error = Strings.MSG_FIELD_BLANK
                return@setOnClickListener
            }

            loginController.loginUser(
                    email, password,
                    object : AuthCallback {
                        override fun onCallback() {
                            val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                            startActivity(intent)
                        }

                        override fun onCallbackError(error: String) {
                            emailField.error = error
                        }
                    }
            )
        }

        registerView.setOnClickListener {
            this.verifyStoragePermissions(this)
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        aboutView.setOnClickListener {
            val intent = Intent(this@LoginActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        forgotView.setOnClickListener {
            val intent = Intent(this@LoginActivity, PalavraChave::class.java)
            startActivity(intent)
        }
    }

    /**
     * Invoked after login via providers.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (facebookRequest) {
            callbackManager.onActivityResult(requestCode, resultCode, data)
        } else {
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    if (account.idToken != null) {
                        val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                        loginController.loginUserWithCredential(credential, object : AuthCallback{
                            override fun onCallback() {
                                loginController.userExists(object : FirebaseDBCallback {
                                    override fun onCallback(list: HashMap<String, Any>) {
                                        if(list.isEmpty()){
                                            verifyStoragePermissions(this@LoginActivity)
                                            val intent = Intent(this@LoginActivity, CustomRegistrationActivity::class.java)
                                            startActivity(intent)
                                        } else {
                                            val intent = Intent(this@LoginActivity, BaseActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }

                                })
                            }

                            override fun onCallbackError(error: String) {
                                throw AuthException(error)
                            }

                        })

                    } else {
                        Log.w(ContentValues.TAG, Strings.MSG_FAIL_USER_LOGIN, task.exception)
                        throw AuthException(task.exception?.message ?: Strings.MSG_FAIL_USER_LOGIN)
                    }
                } catch (e: ApiException) {
                    Log.w(ContentValues.TAG, Strings.MSG_FAIL_USER_LOGIN, e.cause)
                    throw AuthException(e?.message ?: Strings.MSG_FAIL_USER_LOGIN)
                }
            }
        }
    }

    /**
     * Request user permission to access external storage.
     *
     * @param activity
     */
    fun verifyStoragePermissions(activity: Activity?) {
        // Check if we have write permission
        val permission = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permission2 = ActivityCompat.checkSelfPermission(
            activity!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        if (permission != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                activity,
                PERMISSIONS_STORAGE,
                REQUEST_EXTERNAL_STORAGE
            )
        }
    }
}