package pt.ipca.escutas.controllers

import android.graphics.Bitmap
import android.media.Image
import android.view.inputmethod.InputMethodSession
import io.grpc.InternalChannelz
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.User
import pt.ipca.escutas.resources.Strings.MSG_STORAGE_EVENT
import pt.ipca.escutas.services.callbacks.*
import pt.ipca.escutas.services.callbacks.EventCallBack
import pt.ipca.escutas.views.fragments.CalendarFragment
import java.io.InputStream
import com.google.firebase.Timestamp
import pt.ipca.escutas.resources.Strings
import java.util.*

/**
 * Defines the [CalendarFragment] controller.
 *
 */
class CalendarController : BaseController() {

private var eventList: ArrayList<Event> = arrayListOf()

/**
* Gets the stored events.
*
* @return A list containing the stored events.
*/
fun getStoredEventsList(callback: EventCallBack) {

if (eventList.size > 0) {
    callback.onCallback(eventList)
} else {
    prepareEvents(callback)
}
}


    fun saveEvent(event: Event, callback: EventCallBack) {

    }

private fun prepareEvents(callback: EventCallBack) {

database.getAllRecords(
        "events",
        object : FirebaseDBCallback {

            override fun onCallback(list: HashMap<String, Any>) {

                list.forEach { (key, value) ->

                    val values = value as HashMap<String, Any>
                    val event = Event(
                                    UUID.randomUUID(),
                                    values["name"] as String,
                                    values["description"] as String,
                                    (values["startDate"] as Timestamp).toDate(),
                                    (values["endDate"] as Timestamp).toDate(),
                                    values["attachment"] as String,
                                    values["shared"] as Boolean,
                            )

                         eventList.add(event)
                        }
                        callback.onCallback(eventList)
                    }
                }
        )
    }


    /**
     * Upload image to the storage service.
     *
     * @param filePath
     * @param fileStream
     */
    fun uploadImage(filePath: String, fileStream: InputStream, callback: StorageCallback) {
        storage.createFile(filePath, fileStream, callback)
    }


    fun addEvent(event: Event, inputStream: InputStream?, eventCallback: EventCallBack) {
        if (inputStream != null && event.attachment.isNotEmpty()) {
            uploadImage(event.attachment,inputStream, object : StorageCallback{
                override fun onCallback(image: Bitmap?) {
                    database.addRecord(Strings.MSG_STORAGE_EVENT, event, object : FirebaseDBCallback{
                        override fun onCallback(list: HashMap<String, Any>) {
                            eventCallback.onCallback(eventList)
                        }

                    })
                }

            })
        } else {
            database.addRecord(Strings.MSG_STORAGE_EVENT, event, object : FirebaseDBCallback {
                override fun onCallback(list: HashMap<String, Any>) {
                    eventCallback.onCallback(eventList)
                }
            })
        }
    }

    /**
     * Retrieves the event image.
     *
     * @param imagePath
     * @param callback
     */
    fun getEventImage(imagePath: String, callback: StorageCallback) {
        storage.readFile(imagePath, callback)
    }



}