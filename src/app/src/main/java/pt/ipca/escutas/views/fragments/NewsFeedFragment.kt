package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news_feed.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.NewsFeedController
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.adapters.NewsFeedAdapter
import pt.ipca.escutas.views.adapters.OnNewItemClickListener
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class NewsFeedFragment : Fragment(), OnNewItemClickListener {

    /**
     * The news feed controller.
     */
    private val newsfeedController: NewsFeedController = NewsFeedController()

    /**
     * The news feed list.
     */
    private var news: List<News> = emptyList()

    /**
     * The layout manager.
     */
    private var layoutManager: RecyclerView.LayoutManager? = null

    /**
     * The News Feed recycler view adapter.
     */
    private var adapter: RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>? = null

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = "Feed"

        val applicationContext = activity!!.applicationContext

        newsfeedController.getStoredNewsList(
            applicationContext,
            object : GenericCallback {
                override fun onCallback(value: Any?) {
                    if (value != null) {
                        var list = value as ArrayList<News>
                        news = list
                        initRecyclerView()
                    }
                }
            }
        )
    }

    /**
     * Populate recycler view with an adapter with all the necessary data.
     *
     */
    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this.context)

        val recycler: RecyclerView = view!!.findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager

        adapter = NewsFeedAdapter(news, this)
        recyclerView.adapter = adapter
    }

    /**
     * Invoked when the fragment instantiates his view.
     *
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance state.
     * @return The fragment view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_feed, container, false)
    }

    companion object {
        /**
         * Gets a instance of the current fragment.
         *
         * @return A instance of the current fragment.
         */
        fun getInstance(): NewsFeedFragment = NewsFeedFragment()
    }
}
