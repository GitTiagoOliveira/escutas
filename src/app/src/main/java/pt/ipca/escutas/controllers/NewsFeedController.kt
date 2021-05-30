package pt.ipca.escutas.controllers

import android.media.Image
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.Group
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.*
import pt.ipca.escutas.views.fragments.CalendarFragment
import java.util.*

/**
 * Defines the [NewsFeedFragment] controller.
 *
 */
class NewsFeedController : BaseController() {

    private var newsList: ArrayList<News> = arrayListOf()

    /**
     * Gets the stored news.
     *
     * @return A list containing the stored news.
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

    /**
     * Retrieves the news image.
     *
     * @param imagePath
     * @param callback
     */
    fun getNewsImage(imagePath: String, callback: StorageCallback) {
        storage.readFile(imagePath, callback)
    }
}
