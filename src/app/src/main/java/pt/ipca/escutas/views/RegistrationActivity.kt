package pt.ipca.escutas.views

import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import de.hdodenhof.circleimageview.CircleImageView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.MapController
import pt.ipca.escutas.controllers.RegistrationController
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.services.callbacks.GroupCallback
import pt.ipca.escutas.utils.DateUtils.DateValue
import pt.ipca.escutas.utils.StringUtils.isValidEmail
import java.util.*


/**
 * Defines the registration activity.
 *
 */
class RegistrationActivity : AppCompatActivity() {

    /**
     * The map controller.
     */
    private val mapController: MapController = MapController()

    /**
     * The registration controller.
     */
    private var registrationController = RegistrationController()

    /**
     * The number representation of android action.
     */
    private var RESULT_LOAD_IMAGE = 1

    /**
     * The profile image uri.
     */
    private lateinit var fileUri: Uri

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mapController.getStoredGroupsList(object : GroupCallback {
            override fun onCallback(list: ArrayList<Group>) {
                var groupNameList: MutableList<String> = arrayOf("Nenhum").toMutableList()
                for(group in list){
                    groupNameList.add(group.name)
                }

                refreshGroupList(groupNameList.toTypedArray());
            }
        })
        val registerButton = findViewById<Button>(R.id.Button_Register)

        registerButton.setOnClickListener {
            registerUser()
        }

        val uploadImage = findViewById<FrameLayout>(R.id.frameLayout_avatar)

        uploadImage.setOnClickListener{
            selectImageInAlbum()
        }

        // toolbar
        val toolbar: Toolbar = findViewById<View>(R.id.toolbar) as Toolbar

        toolbar.title = "Registo"
        setSupportActionBar(toolbar)

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        }


    }

    /**
     * Selects user image for profile data.
     *
     */
    fun selectImageInAlbum() {

        val i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(i, RESULT_LOAD_IMAGE)
    }

    /**
     * Populates group list for selection.
     *
     */
    private fun refreshGroupList(items: Array<String>) {
        val dropdown = findViewById<Spinner>(R.id.editText_group)
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter
    }

    /**
     * Registers the user through the specified input data.
     *
     */
    private fun registerUser() {
        val emailField = findViewById<EditText>(R.id.editText_email)
        val email = emailField.text.toString().trim()

        if (email.isEmpty()) {
            emailField.error = Strings.MSG_FIELD_BLANK
            return
        }

        if (!email.isValidEmail()) {
            emailField.error = Strings.MSG_INCORRECT_EMAIL
            return
        }

        val nameField = findViewById<EditText>(R.id.editText_username)
        val name = nameField.text.toString().trim()

        if (name.isEmpty()) {
            nameField.error = Strings.MSG_FIELD_BLANK
            return
        }

        val birthdayPicker = findViewById<DatePicker>(R.id.datePicker_birthday)
        val birthday = DateValue(birthdayPicker.year, birthdayPicker.month, birthdayPicker.dayOfMonth)

        val passwordField = findViewById<EditText>(R.id.editText_password)
        val password = passwordField.text.toString().trim()

        if (password.isEmpty()) {
            passwordField.error = Strings.MSG_FIELD_BLANK
            return
        }

        if (password.length < 6) {
            passwordField.error = Strings.MSG_SMALL_PASSWORD
            return
        }

        val repeatedPasswordField = findViewById<EditText>(R.id.editText_password_repeat)
        val repeatedPassword = repeatedPasswordField.text.toString().trim()

        if (repeatedPassword.isEmpty()) {
            repeatedPasswordField.error = Strings.MSG_FIELD_BLANK
            return
        }

        if (repeatedPassword != password) {
            repeatedPasswordField.error = Strings.MSG_INCORRECT_PASSWORDS
            return
        }

        val groupSpinner = findViewById<Spinner>(R.id.editText_group)
        val group = groupSpinner.selectedItem.toString()

        var inputStream = contentResolver.openInputStream(fileUri!!)

        var imagePath = ""

        if (inputStream != null) {
            imagePath = "users/" + UUID.randomUUID() + ".png"
        }

        var user = User(
            UUID.randomUUID(),
            imagePath,
            email,
            name,
            password,
            birthday,
            group)

        registrationController.addUser(user, object : AuthCallback {
                override fun onCallback() {
                    var inputStream = contentResolver.openInputStream(fileUri!!)
                    registrationController.saveUser(user, inputStream, object : AuthCallback {
                        override fun onCallback() {
                            finish()
                        }

                        override fun onCallbackError(error: String) {
                            emailField.error = error
                        }
                    })

                }

            override fun onCallbackError(error: String) {
                emailField.error = error
            }
        })
    }

    /**
     * This method provides selected image Uri and populated circle image view.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data!!
    }

        val selectedImage: Uri = fileUri
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor: Cursor? = contentResolver.query(
            selectedImage,
            filePathColumn, null, null, null
        )

        if(cursor != null) {
            cursor.moveToFirst()

            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
            val picturePath: String = cursor.getString(columnIndex)

            cursor.close()
            val image = findViewById<CircleImageView>(R.id.frameLayout_circleimage)
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
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
