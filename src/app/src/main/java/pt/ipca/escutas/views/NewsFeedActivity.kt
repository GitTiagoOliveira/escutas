package pt.ipca.escutas.views

import android.os.Bundle
import pt.ipca.escutas.R

/**
 * Defines the news feed activity.
 *
 */
class NewsFeedActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)
    }
}
