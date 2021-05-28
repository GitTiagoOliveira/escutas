package pt.ipca.escutas.services.callbacks

import pt.ipca.escutas.models.News
import java.util.ArrayList

interface NewsCallBack {
    fun onCallback(list: ArrayList<News>)
}