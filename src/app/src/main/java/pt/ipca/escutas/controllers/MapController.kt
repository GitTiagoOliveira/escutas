package pt.ipca.escutas.controllers

import android.content.ContentValues
import android.content.Context
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.resources.Strings.MSG_STORAGE_GROUP_LOCATION
import pt.ipca.escutas.services.SqliteDatabaseService
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.utils.NetworkUtils
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
    fun getStoredGroupsList(context: Context, callback: GenericCallback) {

        if (groupList.size > 0) {
            callback.onCallback(groupList)
        } else {
            prepareGroups(context, callback)
        }
    }

    /**
     * Retrieves all groups from database service and populates cache [groupList].
     *
     * @param callback
     */
    private fun prepareGroups(context: Context, callback: GenericCallback) {

        var sqliteService = SqliteDatabaseService(context)

        if (NetworkUtils.isNetworkAvailable(context) && (NetworkUtils.isWifiOn(context) || NetworkUtils.checkMobileDataIsEnabled(context))) {
            database.getAllRecords(
                MSG_STORAGE_GROUP_LOCATION,
                object : GenericCallback {
                    override fun onCallback(value: Any?) {

                        sqliteService.deleteRecord(MSG_STORAGE_GROUP_LOCATION, null)

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

                            var cv = ContentValues()
                            cv.put("name", group.name)
                            cv.put("description", group.description)
                            cv.put("latitude", group.latitude)
                            cv.put("longitude", group.longitude)
                            sqliteService.addRecord(MSG_STORAGE_GROUP_LOCATION, cv, callback)

                            groupList.add(group)
                        }
                        callback.onCallback(groupList)
                    }
                }
            )
        } else {
            // Retrieve from cache
            sqliteService.getAllRecords(MSG_STORAGE_GROUP_LOCATION, callback)
        }
    }
}
