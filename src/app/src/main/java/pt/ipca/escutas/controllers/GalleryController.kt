package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.Album
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.services.callbacks.StorageCallback
import pt.ipca.escutas.views.fragments.GalleryFragment
import java.util.*

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
    fun getImage(imagePath: String, callback: StorageCallback) {
        storage.readFile(imagePath, callback)
    }

    /**
     * Retrieves the path of available images.
     *
     * @param imagePath
     * @param callback
     */
    fun getImagesPath(eventName: String, callback: GenericCallback) {
        storage.listFolder("albums/".plus(eventName), callback);
    }

    private fun prepareEvents(callback: GenericCallback) {
        database.getAllRecords(
            "events",
            object : FirebaseDBCallback {

                override fun onCallback(list: HashMap<String, Any>) {
                    list.forEach { (_, value) ->
                        val values = value as HashMap<String, Any>
                        val event = Album(
                            UUID.randomUUID(),
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
