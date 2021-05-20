package pt.ipca.escutas.services.callbacks

import java.util.ArrayList

interface LocationCallback {
    fun onCallback(list: ArrayList<pt.ipca.escutas.models.Location>)
}