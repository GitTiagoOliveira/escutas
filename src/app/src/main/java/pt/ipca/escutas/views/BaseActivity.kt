package pt.ipca.escutas.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import pt.ipca.escutas.R

/**
 * Defines the base activity. This activity defines the base layout, state and behavior.
 *
 */
open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
}