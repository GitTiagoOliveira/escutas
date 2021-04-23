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

        val aboutView = findViewById<TextView>(R.id.sobre)
        val registerView = findViewById<TextView>(R.id.Button_Register)

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
