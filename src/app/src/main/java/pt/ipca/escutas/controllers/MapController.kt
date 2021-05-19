package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.Location
import pt.ipca.escutas.services.callbacks.FirebaseCallback
import pt.ipca.escutas.views.fragments.MapFragment
import java.util.*
import java.util.concurrent.Semaphore
import java.util.concurrent.locks.ReentrantLock

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
    fun getLocations(): List<Location> {

        return locationList
    }

    fun prepareLocations(mapFragment: MapFragment) {

        database.getAllRecords(
            "groups",
            object : FirebaseCallback {

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

                    mapFragment.onMapReady(mapFragment.getMap())
                }
            }
        )
    }
}
