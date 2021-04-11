package pt.ipca.escutas.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pt.ipca.escutas.R

/**
 * Defines the splash screen activity.
 *
 */
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }
}