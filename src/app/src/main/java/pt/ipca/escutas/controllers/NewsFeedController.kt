package pt.ipca.escutas.controllers

import android.media.Image
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.EventCallBack
import pt.ipca.escutas.services.callbacks.FirebaseDBCallback
import pt.ipca.escutas.services.callbacks.GroupCallback
import pt.ipca.escutas.services.callbacks.NewsCallBack
import pt.ipca.escutas.views.fragments.CalendarFragment
import java.util.*

/**
 * Defines the [CalendarFragment] controller.
 *
 */
class NewsFeedController : BaseController() {

    private var newsList: ArrayList<News> = arrayListOf()

    /**
     * Gets the stored events.
     *
     * @return A list containing the stored events.
     */
    fun getStoredNewsList(callback: NewsCallBack) {

        if (newsList.size > 0) {
            callback.onCallback(newsList)
        } else {
            prepareNews(callback)
        }
    }

    private fun prepareNews(callback: NewsCallBack) {

        database.getAllRecords(
                "news",
                object : FirebaseDBCallback {

                    override fun onCallback(list: HashMap<String, Any>) {

                        list.forEach { (key, value) ->

                            val values = value as HashMap<String, Any>
                            val news = News(
                                    UUID.randomUUID(),
                                    values["title"] as String,
                                    values["body"] as String,
                                    values["image"] as String
                            )

                            newsList.add(news)
                        }
                        callback.onCallback(newsList)
                    }
                }
        )
    }
}
