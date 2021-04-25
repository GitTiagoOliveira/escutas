package pt.ipca.escutas.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R

/**
 * Defines the about activity.
 *
 */
class AboutActivity : AppCompatActivity() {
    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}
