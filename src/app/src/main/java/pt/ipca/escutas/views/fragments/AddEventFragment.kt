package pt.ipca.escutas.views.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.fragment_add_event.*
import kotlinx.android.synthetic.main.fragment_calendar.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.CalendarController
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.callbacks.AuthCallback
import pt.ipca.escutas.services.callbacks.EventCallBack
import pt.ipca.escutas.utils.DateUtils
import pt.ipca.escutas.utils.StringUtils.isValidEmail
import java.io.InputStream
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class AddEventFragment : Fragment() {


    private var fileUri: Uri? = null

    /**
     * The photo upload stream.
     */
    private var inputStream: InputStream? = null

    /**
     * The calendar controller.
     */
    private val calendarController: CalendarController = CalendarController()

    /**
     * The number representation of android action.
     */
    private var RESULT_LOAD_IMAGE = 111



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
                    editText_beginDate.setText(getString(R.string.date_string, mYear, mMonth + 1, mdayOfMonth))
                },
                year, month, day
            )

            dpd.show()
        }

        editText_endDate.setOnClickListener {
            val dpd = DatePickerDialog(
                this.context!!,
                DatePickerDialog.OnDateSetListener { view, mYear, mMonth, mdayOfMonth ->
                    editText_endDate.setText(getString(R.string.date_string, mYear, mMonth + 1, mdayOfMonth))
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

        add_file.setOnClickListener {
            val intent = Intent().setType("*/*").setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)
        }

        Button_registo.setOnClickListener {
            addEvent()
        }


    }
    /**
     * This method provides selected image Uri
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == AppCompatActivity.RESULT_OK && data != null && data.data != null) {
            fileUri = data.data!!

            if (fileUri != null) {
                inputStream = activity?.contentResolver?.openInputStream(fileUri!!)
            }

            var imagePath = ""

            if (inputStream != null) {
                imagePath = "" + UUID.randomUUID() + ".png"
            }

            editText_group.setText(imagePath)
        }

        val selectedImage: Uri = fileUri!!
        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

        val cursor: Cursor? = activity?.contentResolver?.query(
                selectedImage,
                filePathColumn, null, null, null
        )
/*
        if(cursor != null) {
            cursor.moveToFirst()

            val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
            val picturePath: String = cursor.getString(columnIndex)

            cursor.close()
            val image = view!!.findViewById<EditText>((R.id.textView_addAnexo))
            //image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }

 */
    }

    /**
     * Registers the event through the specified input data.
     *
     */
    fun addEvent() {
        val eventNameField = view!!.findViewById<EditText>(R.id.editText_eventname)
        val eventName = eventNameField.text.toString().trim()

        if (eventName.isEmpty()) {
            eventNameField.error = Strings.MSG_FIELD_BLANK
            return
        }


        val eventDescriptionField = view!!.findViewById<EditText>(R.id.editText_descEvent)
        val description = eventDescriptionField.text.toString().trim()

        if (description.isEmpty()) {
            eventDescriptionField.error = Strings.MSG_FIELD_BLANK
            return
        }

        val beginDatePicker = view!!.findViewById<EditText>(R.id.editText_beginDate)
        val beginDate = beginDatePicker.text.toString().trim()
        var date = Date()

        val endDatePicker = view!!.findViewById<EditText>(R.id.editText_endDate)
        val endDate = endDatePicker.text.toString().trim()
        var date2 = Date()

        val beginHourPicker = view!!.findViewById<EditText>(R.id.editText_beginHour)
        val beginHour = beginHourPicker.text.toString().trim()

        val endHourPicker = view!!.findViewById<EditText>(R.id.editText_endHour)
        val endHour = endHourPicker.text.toString().trim()

        if (beginDate.isEmpty()) {
            beginDatePicker.error = Strings.MSG_FIELD_BLANK
            return
        } else {
            date = SimpleDateFormat("yyyy-MM-dd").parse(beginDate)
        }

        if (endDate.isEmpty()) {
            endDatePicker.error = Strings.MSG_FIELD_BLANK
            return
        } else {
            date2 = SimpleDateFormat("yyyy-MM-dd").parse(endDate)
        }

        if (date2.before(date)) {
            endDatePicker.error = Strings.MSG_DATE_INVALID
            return
        }


        if (beginHour.isEmpty()) {
            beginHourPicker.error = Strings.MSG_FIELD_BLANK
            return
        }

        if (endHour.isEmpty()) {
            endHourPicker.error = Strings.MSG_FIELD_BLANK
            return
        }

        //val attachmentField = view!!.findViewById<EditText>(R.id.textView_addAnexo)
        //val attachment = attachmentField.text.toString().trim()

        if (fileUri != null) {
            inputStream = activity?.contentResolver?.openInputStream(fileUri!!)
        }

        var imagePath = ""

        if (inputStream != null) {
            imagePath = "events/" + UUID.randomUUID() + ".png"
        }


        val shareAllField = view!!.findViewById<CheckBox>(R.id.checkbox_share)
        val share = shareAllField.isChecked


        var event = Event(
                UUID.randomUUID(),
                eventName,
                description,
                date,
                date2,
                imagePath,
                share)

        calendarController.addEvent(event, inputStream, object : EventCallBack {
            override fun onCallback(list: ArrayList<Event>) {

            }

            override fun onCallback() {

            }
        })



        val fragment = CalendarFragment()
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }



}
