package pt.ipca.escutas.views

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.LoginController
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.AuthCallback

class PalavraChave : AppCompatActivity() {

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
        setContentView(R.layout.activity_password_recovery)

        // toolbar
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        toolbar.title = "Recuperar Palavra-Chave"
        setSupportActionBar(toolbar)

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        }

        val submit = findViewById<Button>(R.id.Button_Recuperar)

        submit.setOnClickListener {
            val emailField = findViewById<EditText>(R.id.editText_email)
            val email = emailField.text.toString().trim()

            if (email.isEmpty()) {
                emailField.error = Strings.MSG_FIELD_BLANK
                return@setOnClickListener
            }

            loginController.resetPassword(
                email,
                object : AuthCallback {
                    override fun onCallback() {
                        finish()
                    }

                    override fun onCallbackError(error: String) {
                        emailField.error = error
                    }
                }
            )
        }
    }

    /**
     * This method implements return toolbar action.
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
