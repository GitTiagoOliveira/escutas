package pt.ipca.escutas.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.LoginController

/**
 * Defines the login activity.
 *
 */
class LoginActivity : AppCompatActivity() {
    /**
     * The login controller.
     */
    private val logController by lazy { LoginController() }

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

            val editText_email = findViewById<EditText>(R.id.editText_login_email)
            val email = editText_email.text.toString().trim()

            val editText_password = findViewById<EditText>(R.id.editText_login_password)
            val password = editText_password.text.toString().trim()

            logController.loginUser(email, password)

            val intent = Intent(this@LoginActivity, BaseActivity::class.java)
            startActivity(intent)
        }

        aboutView.setOnClickListener {
            val intent = Intent(this@LoginActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        registerView.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
