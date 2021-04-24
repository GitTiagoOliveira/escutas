package pt.ipca.escutas.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import pt.ipca.escutas.R

/**
 * Defines the base activity. This activity defines the base layout, state and behavior.
 *
 */
open class BaseActivity : AppCompatActivity() {
    /**
     * The toolbar.
     */
    protected lateinit var toolbar: Toolbar

    /**
     * The navigation menu.
     */
    protected lateinit var navigationMenu: BottomNavigationView

    /**
     * The navigation menu item listener.
     */
    private val onNavigationMenuItemListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_newsfeed -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_news_feed)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_map)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_calendar -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_calendar)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_gallery -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_gallery)
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

        this.toolbar = findViewById(R.id.toolbar)
        this.navigationMenu = findViewById(R.id.navigation_menu)

        setSupportActionBar(this.toolbar)
        this.navigationMenu.setOnNavigationItemSelectedListener(onNavigationMenuItemListener)
    }
}
