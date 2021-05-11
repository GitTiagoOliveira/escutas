package pt.ipca.escutas.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.LoginController
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.utils.StringUtils.isValidEmail

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
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val baseView = findViewById<Button>(R.id.Button_login)
        val aboutView = findViewById<TextView>(R.id.sobre)
        val registerView = findViewById<TextView>(R.id.Button_Register)

        baseView.setOnClickListener {
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
}
