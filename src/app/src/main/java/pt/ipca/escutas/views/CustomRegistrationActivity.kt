package pt.ipca.escutas.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.MapController
import pt.ipca.escutas.controllers.RegistrationController
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.services.callbacks.GroupCallback
import pt.ipca.escutas.utils.DateUtils.DateValue
import java.io.InputStream
import java.util.*


/**
 * Defines the registration activity.
 *
 */
class CustomRegistrationActivity : AppCompatActivity() {

    /**
     * The map controller.
     */
    private val mapController: MapController = MapController()

    /**
     * The registration controller.
     */
    private var registrationController = RegistrationController()


    private var RESULT_LOAD_IMAGE = 1

    private var fileUri: Uri? = null

    private var inputStream: InputStream? = null

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_activity_registration)

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


    }


    fun selectImageInAlbum() {

        val i = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )

        startActivityForResult(i, RESULT_LOAD_IMAGE)

    }

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

        val email = registrationController.getCustomUserEmail()
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


        var imagePath = ""

        if (fileUri != null) {
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

        if(fileUri !== null) {
                    inputStream = contentResolver.openInputStream(fileUri!!)
        }

        registrationController.saveUser(user, inputStream, object: AuthCallback{
            override fun onCallback() {
                val intent = Intent(this@CustomRegistrationActivity, BaseActivity::class.java)
                startActivity(intent)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null && data.data != null) {
            fileUri = data.data!!
        }
    }
}
