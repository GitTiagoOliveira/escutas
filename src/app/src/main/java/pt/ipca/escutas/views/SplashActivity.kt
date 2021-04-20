package pt.ipca.escutas.views

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R


/**
 * Defines the splash screen activity.
 *
 */
class SplashActivity : AppCompatActivity() {

    val handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        handler.postDelayed(Runnable {
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
