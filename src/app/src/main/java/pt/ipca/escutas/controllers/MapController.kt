package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.Location
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.callbacks.LocationCallback
import pt.ipca.escutas.views.fragments.MapFragment
import java.util.*

/**
 * Defines the [MapFragment] controller.
 *
 */
class MapController : BaseController() {

    private var locationList: ArrayList<Location> = arrayListOf()

    /**
     * Gets the stored locations.
     *
     * @return A list containing the stored locations.
     */
    fun getStoredLocationsList(callback: LocationCallback) {

        if (locationList.size > 0) {
            callback.onCallback(locationList)
        } else {
            prepareLocations(callback)
        }
    }

    fun prepareLocations(callback: LocationCallback) {

        database.getAllRecords(
            "groups",
            object : FirebaseDBCallback {

                override fun onCallback(list: HashMap<String, Any>) {

                    list.forEach { (key, value) ->

                        val values = value as HashMap<String, Any>
                        val tempLocation = Location(
                            UUID.randomUUID(),
                            values["name"] as String,
                            values["description"] as String,
                            values["latitude"] as Double,
                            values["longitude"] as Double
                        )

                        locationList.add(tempLocation)
                    }
                    callback.onCallback(locationList)
                }
            }
        )
    }
}
