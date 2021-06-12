package pt.ipca.escutas.controllers

import android.content.ContentValues
import android.content.Context
import pt.ipca.escutas.models.News
import pt.ipca.escutas.resources.Strings
import pt.ipca.escutas.services.SqliteDatabaseService
import pt.ipca.escutas.services.callbacks.*
import pt.ipca.escutas.utils.NetworkUtils
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
    fun getStoredNewsList(context: Context, callback: GenericCallback) {

        if (newsList.size > 0) {
            callback.onCallback(newsList)
        } else {
            prepareNews(context, callback)
        }
    }

    private fun prepareNews(context: Context, callback: GenericCallback) {

        var sqliteService = SqliteDatabaseService(context)

        if (NetworkUtils.isNetworkAvailable(context) && (NetworkUtils.isWifiOn(context) || NetworkUtils.checkMobileDataIsEnabled(context))) {
            database.getAllRecords(
                Strings.MSG_STORAGE_NEWS_LOCATION,
                object : GenericCallback {
                    override fun onCallback(value: Any?) {

                        sqliteService.deleteRecord(Strings.MSG_STORAGE_NEWS_LOCATION, null)

                        var list = value as HashMap<String, Any>
                        list.forEach { (key, value) ->

                            val values = value as HashMap<String, Any>
                            val idMap = value["id"] as HashMap<String, Long>
                            val news = News(
                                UUID(idMap.get("mostSignificantBits")!!,idMap.get("leastSignificantBits")!!),
                                values["title"] as String,
                                values["body"] as String,
                                values["details"] as String,
                                values["image"] as String
                            )

                            var cv = ContentValues()
                            cv.put("title", news.title)
                            cv.put("body", news.body)
                            cv.put("details", news.details)
                            cv.put("image", news.image)
                            sqliteService.addRecord(Strings.MSG_STORAGE_NEWS_LOCATION, cv, callback)

                            newsList.add(news)
                        }
                        callback.onCallback(newsList)
                    }
                }
            )
        } else {
            // Retrieve from cache
            sqliteService.getAllRecords(Strings.MSG_STORAGE_NEWS_LOCATION, callback)
        }
    }

    /**
     * Retrieves the news image.
     *
     * @param imagePath
     * @param callback
     */
    fun getNewsImage(imagePath: String, callback: GenericCallback) {
        storage.readFile(imagePath, callback)
    }
}
