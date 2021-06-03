package pt.ipca.escutas.views

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.ProfileController
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.fragments.CalendarFragment
import pt.ipca.escutas.views.fragments.GalleryFragment
import pt.ipca.escutas.views.fragments.MapFragment

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
     * The profile controller.
     */
    private val profileController by lazy { ProfileController() }

    /**
     * The navigation menu item listener.
     */
    private val onNavigationMenuItemListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_newsfeed -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_news_feed)
                // TODO: Open news feed fragment when the respective activities are converted.
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_map -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_map)
                openFragment(MapFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_calendar -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_calendar)
                openFragment(CalendarFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_gallery -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_gallery)
                openFragment(GalleryFragment.getInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    /**
     * Opens the specified fragment.
     *
     * @param fragment The fragment to be opened.
     */
    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(null)
            .commit()
    }

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val imageLayout = findViewById<CircleImageView>(R.id.toolbar_profile_avatar)

        profileController.getUser(object : GenericCallback {
            override fun onCallback(value: Any?) {
                if (value != null) {
                    var user = value as User
                    if (user.photo != null && user.photo != "") {
                        profileController.getUserImage(
                            user.photo,
                            object : GenericCallback {
                                override fun onCallback(value: Any?) {
                                    if (value != null) {
                                        var image = value as Bitmap
                                        imageLayout.setImageBitmap(image)
                                        profileController.saveImage(image)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        })

        imageLayout.setOnClickListener {
            val intent = Intent(this@BaseActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        this.toolbar = findViewById(R.id.toolbar)
        this.navigationMenu = findViewById(R.id.navigation_menu)

        setSupportActionBar(this.toolbar)
        this.navigationMenu.setOnNavigationItemSelectedListener(onNavigationMenuItemListener)
        this.navigationMenu.selectedItemId = R.id.navigation_newsfeed
    }
}
