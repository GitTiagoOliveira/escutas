package pt.ipca.escutas.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.view.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.LoginController
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.utils.StringUtils.isValidEmail
import java.util.*


/**
 * Defines the login activity.
 *
 */
class LoginActivity : AppCompatActivity() {
    /**
     * The login controller.
     */
    private val loginController by lazy { LoginController() }
    private val callbackManager = CallbackManager.Factory.create();
    private val RC_SIGN_IN = 123
    private var facebookRequest = true

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


        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(getString(R.string.default_web_client_id))
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        // Set the dimensions of the sign-in button.
        val signInButton = findViewById<SignInButton>(R.id.gmail_login_button)
        signInButton.setSize(SignInButton.SIZE_STANDARD)
        signInButton.setOnClickListener {
            facebookRequest = false
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }


        val facebooklogin = findViewById<View>(R.id.facebook_login_button) as LoginButton
        facebooklogin.setOnClickListener{
            facebookRequest = true
        }
        facebooklogin.setReadPermissions("public_profile email")
        facebooklogin.registerCallback(callbackManager, object : FacebookCallback<LoginResult?> {
            override fun onSuccess(loginResult: LoginResult?) {

                var credential = FacebookAuthProvider.getCredential(loginResult?.accessToken?.getToken())
                loginController.loginUserWithCredential(credential)

                val userId = loginResult?.accessToken?.userId
            }

            override fun onCancel() {
                // App code
            }

            override fun onError(exception: FacebookException) {
                // App code
            }
        })

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

            loginController.loginUser(email, password)

            val intent = Intent(this@LoginActivity, BaseActivity::class.java)
            startActivity(intent)
        }

        registerView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        aboutView.setOnClickListener {
            val intent = Intent(this@LoginActivity, AboutActivity::class.java)
            startActivity(intent)
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(facebookRequest){
            callbackManager.onActivityResult(requestCode, resultCode, data)
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            super.onActivityResult(requestCode, resultCode, data)

            // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
            if (requestCode == RC_SIGN_IN) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                    loginController.loginUserWithCredential(credential)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("Google sign in failed", e)
                }
            }
        }
    }
}
