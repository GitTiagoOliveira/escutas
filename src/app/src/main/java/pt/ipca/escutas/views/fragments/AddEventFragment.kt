package pt.ipca.escutas.views.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_event.*
import pt.ipca.escutas.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class AddEventFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        editText_beginDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this.context!!,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mdayOfMonth ->
                    editText_beginDate.setText(getString(R.string.date_string, mYear, mMonth, mdayOfMonth))
                },
                year, month, day
            )

            dpd.show()
        }

        editText_endDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this.context!!,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mdayOfMonth ->
                    editText_endDate.setText(getString(R.string.date_string, mYear, mMonth, mdayOfMonth))
                },
                year, month, day
            )

            dpd.show()
        }

        editText_beginHour.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)

                editText_beginHour.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        editText_endHour.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                cal.set(Calendar.MINUTE, minute)

                editText_endHour.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(this.context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
        }

        editText_group.setOnClickListener {
            val intent = Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedFile = data?.data
            val filepath = selectedFile.toString()

            editText_group.setText(filepath)
        }
    }
}
