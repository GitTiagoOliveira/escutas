package pt.ipca.escutas.views

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R

/**
 * Defines the login activity.
 *
 */
class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Sobre Click Action
        var aboutTextView = findViewById<TextView>(R.id.sobre)
        aboutTextView.setOnClickListener{
            val intent = Intent(this@LoginActivity, AboutActivity::class.java)
            startActivity(intent)
        }

        // Registration Click Action
        var registerTextView = findViewById<TextView>(R.id.register)
        registerTextView.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}
