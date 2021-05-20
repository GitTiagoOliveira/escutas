package pt.ipca.escutas.services.callbacks

import java.util.ArrayList

interface GroupCallback {
    fun onCallback(list: ArrayList<pt.ipca.escutas.models.Group>)
}
