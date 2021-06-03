package pt.ipca.escutas.views

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.hdodenhof.circleimageview.CircleImageView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.ProfileController
import pt.ipca.escutas.models.User
import pt.ipca.escutas.services.callbacks.StorageCallback
import pt.ipca.escutas.services.callbacks.UserCallback
import java.util.Calendar

/**
 * The profile controller.
 */
private val profileController by lazy { ProfileController() }

/**
 * Defines the profile fragment.
 *
 */
class ProfileActivity : AppCompatActivity() {
    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // toolbar
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        val imageLayout = findViewById<CircleImageView>(R.id.frameLayout_circleimage)

        val emailText = findViewById<TextView>(R.id.textView_emailUser)
        val nameText = findViewById<TextView>(R.id.textView_nameUser)
        val birthdayText = findViewById<TextView>(R.id.textView_birthdayUser)
        val groupText = findViewById<TextView>(R.id.textView_groupUser)

        profileController.getUser(object : UserCallback {
            override fun onCallback(user: User) {
                if (user.photo != null && user.photo != "") {
                    profileController.getUserImage(
                        user.photo,
                        object : StorageCallback {
                            override fun onCallback(image: Bitmap?) {
                                if (image != null) {
                                    imageLayout.setImageBitmap(image)
                                    profileController.saveImage(image)
                                    profileController.getUser(object : UserCallback {
                                        override fun onCallback(user: User) {
                                            nameText.setText(user.name)
                                            emailText.setText(user.email)
                                            var calendar = Calendar.getInstance()
                                            calendar.time = user.birthday
                                            birthdayText.setText(calendar[Calendar.DAY_OF_MONTH].toString() + "-" + calendar[Calendar.MONTH].toString() + "-" + calendar[Calendar.YEAR])
                                            groupText.setText(user.groupName)
                                        }
                                    })
                                }
                            }
                        }
                    )
                }
            }
        })

        toolbar.title = "Área Pessoal"
        setSupportActionBar(toolbar)

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
            getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        }

        val logoutButton = findViewById<Button>(R.id.Button_logout)

        logoutButton.setOnClickListener {
            profileController.logoutUser(this@ProfileActivity)
            val intent = Intent(this@ProfileActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * This method implements return toolbar action.
     *
     * @param item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        if (item.getItemId() === android.R.id.home) {
            finish() // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item)
    }
}
