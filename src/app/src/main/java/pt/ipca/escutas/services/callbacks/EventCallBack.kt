package pt.ipca.escutas.services.callbacks

import pt.ipca.escutas.models.Event
import java.util.ArrayList

interface EventCallBack {
    fun onCallback(list: ArrayList<Event>)
}