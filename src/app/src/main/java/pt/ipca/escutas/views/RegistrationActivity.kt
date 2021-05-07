package pt.ipca.escutas.views

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.RegistrationController
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.utils.DateUtils.DateValue
import pt.ipca.escutas.utils.StringUtils.isValidEmail
import java.util.UUID

/**
 * Defines the registration activity.
 *
 */
class RegistrationActivity : AppCompatActivity() {
    /**
     * The registration controller.
     */
    private var registrationController = RegistrationController()

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val dropdown = findViewById<Spinner>(R.id.editText_group)
        val items = arrayOf("Nenhum", "Agrupamento 1 - Sé de Braga")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        val registerButton = findViewById<Button>(R.id.Button_Register)

        registerButton.setOnClickListener {
            registerUser()
            finish()
        }

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

        registrationController.addUser(User(
            UUID.randomUUID(),
            null,
            email,
            name,
            password,
            birthday,
            null))
    }
}
