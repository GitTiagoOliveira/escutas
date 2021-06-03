package pt.ipca.escutas.controllers

import pt.ipca.escutas.models.Group
import pt.ipca.escutas.resources.Strings.MSG_STORAGE_GROUP_LOCATION
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.fragments.MapFragment
import java.util.UUID

/**
 * Defines the [MapFragment] controller.
 *
 */
class MapController : BaseController() {

    /**
     * Internal group list cache.
     */
    private var groupList: ArrayList<Group> = arrayListOf()

    /**
     * Gets the stored locations.
     *
     * @return A list containing the stored locations.
     */
    fun getStoredGroupsList(callback: GenericCallback) {

        if (groupList.size > 0) {
            callback.onCallback(groupList)
        } else {
            prepareGroups(callback)
        }
    }

    /**
     * Retrieves all groups from database service and populates cache [groupList].
     *
     * @param callback
     */
    private fun prepareGroups(callback: GenericCallback) {

        database.getAllRecords(
            MSG_STORAGE_GROUP_LOCATION,
            object : GenericCallback {
                override fun onCallback(value: Any?) {

                    var list = value as HashMap<String, Any>
                    list.forEach { (key, value) ->

                        val values = value as HashMap<String, Any>
                        val group = Group(
                            UUID.randomUUID(),
                            values["name"] as String,
                            values["description"] as String,
                            values["latitude"] as Double,
                            values["longitude"] as Double
                        )

                        groupList.add(group)
                    }
                    callback.onCallback(groupList)
                }
            }
        )
    }
}
