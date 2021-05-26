package pt.ipca.escutas.views

import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
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

    /**
     * The number representation of android action.
     */
    private var RESULT_LOAD_IMAGE = 1

    /**
     * The profile image uri.
     */
    private var fileUri: Uri? = null

    /**
     * The photo upload stream.
     */
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

        val email = registrationController.getCustomUserEmail()
        val nameField = findViewById<EditText>(R.id.editText_username)
        val name = nameField.text.toString().trim()

        if (name.isEmpty()) {
            nameField.error = Strings.MSG_FIELD_BLANK
            return
        }

        val birthdayPicker = findViewById<DatePicker>(R.id.datePicker_birthday)
        val birthday = DateValue(birthdayPicker.year, birthdayPicker.month, birthdayPicker.dayOfMonth)

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

            override fun onCallbackError(error: String) {
                nameField.error = error
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

        val selectedImage: Uri = fileUri!!
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
}
