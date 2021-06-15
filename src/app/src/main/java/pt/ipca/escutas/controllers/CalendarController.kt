package pt.ipca.escutas.controllers

import android.content.ContentValues
import android.content.Context
import com.google.firebase.Timestamp
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.SqliteDatabaseService
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.utils.NetworkUtils
import pt.ipca.escutas.views.fragments.CalendarFragment
import java.io.InputStream
import java.util.UUID

/**
 * Defines the [CalendarFragment] controller.
 *
 */
class CalendarController : BaseController() {

    /**
     * Save event data for further requests from the same fragment.
     */
    private var eventList: ArrayList<Event> = arrayListOf()

    /**
     * Gets the stored events.
     *
     * @return A list containing the stored events.
     */
    fun getStoredEventsList(context: Context, callback: GenericCallback) {

        if (eventList.size > 0) {
            callback.onCallback(eventList)
        } else {
            prepareEvents(context, callback)
        }
    }

    /**
     * Populate SQLite Service with necessary data and retrieve from database.
     * If no network access is available read directly from SQLite.
     *
     * @param context
     * @param callback
     */
    private fun prepareEvents(context: Context, callback: GenericCallback) {

        var sqliteService = SqliteDatabaseService(context)

        if (NetworkUtils.isNetworkAvailable(context) && (NetworkUtils.isWifiOn(context) || NetworkUtils.checkMobileDataIsEnabled(context))) {
            database.getAllRecords(
                Strings.MSG_STORAGE_EVENT_LOCATION,
                object : GenericCallback {
                    override fun onCallback(value: Any?) {
                        sqliteService.deleteRecord(Strings.MSG_STORAGE_EVENT_LOCATION, null)
                        var list = value as HashMap<String, Any>
                        list.forEach { (key, value) ->

                            val values = value as HashMap<String, Any>
                            val idMap = value["id"] as HashMap<String, Long>
                            val event = Event(
                                UUID(idMap.get("mostSignificantBits")!!, idMap.get("leastSignificantBits")!!),
                                values["name"] as String,
                                values["description"] as String,
                                (values["startDate"] as Timestamp).toDate(),
                                (values["endDate"] as Timestamp).toDate(),
                                values["attachment"] as String,
                                values["shared"] as Boolean,
                            )

                            var cv = ContentValues()
                            cv.put("name", event.name)
                            cv.put("description", event.description)
                            cv.put("startDate", event.startDate.toString())
                            cv.put("endDate", event.endDate.toString())
                            cv.put("attachment", event.attachment)
                            cv.put("shared", event.Shared)
                            sqliteService.addRecord(Strings.MSG_STORAGE_EVENT_LOCATION, cv, callback)

                            eventList.add(event)
                        }

                        eventList.sortBy { it.startDate }

                        callback.onCallback(eventList)
                    }
                }
            )
        } else {
            sqliteService.getAllRecords(Strings.MSG_STORAGE_EVENT_LOCATION, callback)
        }
    }

    /**
     * Upload image to the storage service.
     *
     * @param filePath
     * @param fileStream
     */
    fun uploadImage(filePath: String, fileStream: InputStream, callback: GenericCallback) {
        storage.createFile(filePath, fileStream, callback)
    }

    /**
     * Adds an event to the calendar
     *
     * @param event
     * @param inputStream
     * @param eventCallback
     */
    fun addEvent(event: Event, inputStream: InputStream?, eventCallback: GenericCallback) {
        if (inputStream != null && event.attachment.isNotEmpty()) {
            uploadImage(
                event.attachment, inputStream,
                object : GenericCallback {
                    override fun onCallback(value: Any?) {
                        database.addRecord(
                            Strings.MSG_STORAGE_EVENT_LOCATION, event,
                            object : GenericCallback {
                                override fun onCallback(value: Any?) {
                                    eventCallback.onCallback(null)
                                }
                            }
                        )
                    }
                }
            )
        } else {
            database.addRecord(
                Strings.MSG_STORAGE_EVENT_LOCATION, event,
                object : GenericCallback {
                    override fun onCallback(lvalue: Any?) {
                        eventCallback.onCallback(null)
                    }
                }
            )
        }
    }

    /**
     * Retrieves the event image.
     *
     * @param imagePath
     * @param callback
     */
    fun getEventImage(imagePath: String, callback: GenericCallback) {
        storage.readFile(imagePath, callback)
    }
}
