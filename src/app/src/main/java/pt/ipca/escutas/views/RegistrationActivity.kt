package pt.ipca.escutas.views

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import pt.ipca.escutas.R

/**
 * Defines the registration activity.
 *
 */
class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val dropdown = findViewById<Spinner>(R.id.editText_group)
        val items = arrayOf("Agrupamento", "Escutas 1", "Escutas 2")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, items)
        dropdown.adapter = adapter
    }

    fun registarUser(view: View) {

        val form_NAME = findViewById(R.id.editText_username) as EditText
        val name = form_NAME.text.toString().trim()

        val form_AGE = findViewById(R.id.editText_age) as DatePicker
        val age = form_AGE.dayOfMonth.toString() + "-" + form_AGE.month.toString() + "-" + form_AGE.year.toString()

        val form_EMAIL = findViewById(R.id.editText_email) as EditText
        val email = form_EMAIL.text.toString().trim()

        val form_PASSWORD1 = findViewById(R.id.editText_password1) as EditText
        val password = form_PASSWORD1.text.toString().trim()

        val form_PASSWORD2 = findViewById(R.id.editText_password2) as EditText
        val password2 = form_PASSWORD2.text.toString().trim()

        val form_GROUP = findViewById(R.id.editText_group) as Spinner
        val group = form_GROUP.toString().trim()

        // val newUser = User(UUID.randomUUID(),null, email, name, password, age, null)

        /*
        mAuth.createUserWithEmailAndPassword(
                email,
                password1)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = mAuth.currentUser

                        //Criar Objecto User
                        val user2 = User(null, name, age, email, password1, group)

                        //Guardar User na BD
                        //FirebaseDatabase.getInstance().getReference("Users").child(mAuth.currentUser.uid)

                        Toast.makeText(this, "User has been registered successfully!", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Error registering user!", Toast.LENGTH_LONG).show();
                    }
                }*/
    }
}
