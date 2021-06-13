package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.Album
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.fragments.GalleryFragment
import java.util.Date
import java.util.UUID
import kotlin.collections.HashMap

/**
 * Defines the [GalleryFragment] controller.
 *
 */
class GalleryController : BaseController() {
    private var eventsList: ArrayList<Album> = arrayListOf()

    /**
     * Gets the stored Events.
     * The Albums are a group of images from Events
     *
     * @return A list containing the stored Albums.
     */
    fun getStoredEventsList(callback: GenericCallback) {

        if (eventsList.size > 0) {
            callback.onCallback(eventsList)
        } else {
            prepareEvents(callback)
        }
    }

    /**
     * Retrieves the Album images.
     *
     * @param imagePath
     * @param callback
     */
    fun getImage(imagePath: String, callback: GenericCallback) {
        storage.readFile(imagePath, callback)
    }

    /**
     * Retrieves the path of available images.
     *
     * @param imagePath
     * @param callback
     */
    fun getImagesPath(eventName: String, callback: GenericCallback) {
        storage.listFolder("albums/".plus(eventName), callback)
    }

    /**
     * Retrieves all events from database service.
     *
     * @param callback
     */
    private fun prepareEvents(callback: GenericCallback) {
        database.getRecordWithLessThanFilter(
            "events",
            "startDate",
            Date(),
            object : GenericCallback {

                override fun onCallback(value: Any?) {

                    var list = value as HashMap<String, Any>
                    list.forEach { (_, value) ->
                        val values = value as HashMap<String, Any>

                        val idMap = value["id"] as java.util.HashMap<String, Long>
                        val event = Album(
                            UUID(idMap.get("mostSignificantBits")!!, idMap.get("leastSignificantBits")!!),
                            values["name"] as String,
                            values["attachment"] as String
                        )
                        eventsList.add(event)
                    }
                    callback.onCallback(eventsList)
                }
            }
        )
    }
}
