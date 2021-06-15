package pt.ipca.escutas.views

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import de.hdodenhof.circleimageview.CircleImageView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.ProfileController
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.fragments.*
import java.util.*

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
     * Double click counter.
     */
    private var mLastClickTime: Long = 0

    /**
     * The navigation menu item listener.
     */
    private val onNavigationMenuItemListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
            return@OnNavigationItemSelectedListener false;
        }

        mLastClickTime = SystemClock.elapsedRealtime();

        when (item.itemId) {
            R.id.navigation_newsfeed -> {
                this.toolbar.setTitle(R.string.menu_bottom_navigation_news_feed)
                openFragment(NewsFeedFragment.getInstance())
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
                openFragment(GalleryFeedFragment.getInstance())
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
            .addToBackStack(fragment.toString())
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

            if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                return@setOnClickListener
            }

            mLastClickTime = SystemClock.elapsedRealtime();
            val intent = Intent(this@BaseActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        this.toolbar = findViewById(R.id.toolbar)
        this.navigationMenu = findViewById(R.id.navigation_menu)

        setSupportActionBar(this.toolbar)
        this.navigationMenu.setOnNavigationItemSelectedListener(onNavigationMenuItemListener)
        this.navigationMenu.selectedItemId = R.id.navigation_newsfeed
    }

    /**
     * Handle fragment back arrow behavior
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            val countFrag = supportFragmentManager.backStackEntryCount
            if (countFrag > 0) {
                supportFragmentManager.popBackStackImmediate()
                if (supportFragmentManager.fragments.isNotEmpty()) {
                    (supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1].activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
                }
            } else {
                super.onBackPressed()
            }
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }
    }
}
