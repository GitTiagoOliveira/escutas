package pt.ipca.escutas.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import pt.ipca.escutas.R

/**
 * Defines the base activity. This activity defines the base layout, state and behavior.
 *
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * The navigation menu item listener.
     */
    private val onNavigationMenuItemListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_newsfeed -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_calendar -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_gallery -> {
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_base)

        val navigationMenu: BottomNavigationView = findViewById(R.id.navigation_menu)
    }
}
