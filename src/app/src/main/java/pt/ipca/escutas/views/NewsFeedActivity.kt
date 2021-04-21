package pt.ipca.escutas.views

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_news_feed.*
import pt.ipca.escutas.R
import pt.ipca.escutas.views.adapters.NewsFeedAdapter

/**
 * Defines the news feed activity.
 *
 */
class NewsFeedActivity : BaseActivity() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_feed)

        layoutManager = LinearLayoutManager(this)

        val recycler: RecyclerView = findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager

        adapter = NewsFeedAdapter()
        recyclerView.adapter = adapter
    }
}
