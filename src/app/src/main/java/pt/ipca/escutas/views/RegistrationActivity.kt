package pt.ipca.escutas.views

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.RegistrationController
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Msg
import pt.ipca.escutas.utils.DateUtils.DateValue
import pt.ipca.escutas.utils.StringUtils.isEmailValid
import java.util.UUID

/**
 * Defines the registration activity.
 *
 */
class RegistrationActivity : AppCompatActivity() {
    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */

    private var regController = RegistrationController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val dropdown = findViewById<Spinner>(R.id.editText_group)
        val items = arrayOf("Agrupamento", "Escutas 1", "Escutas 2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)

        dropdown.adapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registarUser(view: View) {

        val form_NAME = findViewById<EditText>(R.id.editText_username)
        val name = form_NAME.text.toString().trim()

        if (form_NAME.getText().length == 0) {
            form_NAME.setError(Msg.MSG_FIELD_BLACK)
        }

        val form_AGE = findViewById<DatePicker>(R.id.editText_age)

        val dateRepresentation = DateValue(form_AGE.year.toInt(), form_AGE.month.toInt(), form_AGE.dayOfMonth.toInt())

        val form_EMAIL = findViewById<EditText>(R.id.editText_email)
        val email = form_EMAIL.text.toString().trim()

        if (form_EMAIL.getText().length == 0) {
            form_EMAIL.setError(Msg.MSG_FIELD_BLACK)
        }

        if (!form_EMAIL.getText().toString().isEmailValid()) {
            form_EMAIL.setError(Msg.MSG_INCORRECT_EMAIL)
        }

        // TODO - Implementar Password policy
        val form_PASSWORD1 = findViewById<EditText>(R.id.editText_password1)
        val password = form_PASSWORD1.text.toString().trim()

        val form_PASSWORD2 = findViewById<EditText>(R.id.editText_password2)
        val password2 = form_PASSWORD2.text.toString().trim()

        if (!password.equals(password2)) {
            form_EMAIL.setError(Msg.MSG_INCORRECT_PASSWORDS)
        }

        val form_GROUP = findViewById<Spinner>(R.id.editText_group)
        val group = form_GROUP.toString().trim()

        if (form_EMAIL.getText().length == 0) {
            form_EMAIL.setError(Msg.MSG_FIELD_BLACK)
        }

        // TODO - Rever tipologia do modelo User
        val newUser = User(UUID.randomUUID(), null, email, name, password, dateRepresentation, null)

        regController.addUser(newUser)
    }
}
