package pt.ipca.escutas.services.callbacks

import java.util.ArrayList

interface GroupCallback {

    /**
     * Returns [list] of groups via callback.
     *
     * @param list
     */
    fun onCallback(list: ArrayList<pt.ipca.escutas.models.Group>)
}
