package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news_feed.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.CalendarController
import pt.ipca.escutas.controllers.NewsFeedController
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.EventCallBack
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.services.callbacks.NewsCallBack
import pt.ipca.escutas.views.adapters.CalendarAdapter
import pt.ipca.escutas.views.adapters.NewsFeedAdapter
import pt.ipca.escutas.views.adapters.OnNewItemClickListener
import java.util.ArrayList


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class NewsFeedFragment : Fragment(), OnNewItemClickListener {

    private lateinit var newsAdapter: NewsFeedAdapter


    /**
     * The news feed controller.
     */
    private val newsfeedController: NewsFeedController = NewsFeedController()

    private var news: List<News> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    private fun initRecyclerView(){
        layoutManager = LinearLayoutManager(this.context)

        val recycler: RecyclerView = view!!.findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager

        adapter = NewsFeedAdapter(news, this)
        recyclerView.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
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

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    public interface OnClickListener {
        fun onClick()
    }

}
