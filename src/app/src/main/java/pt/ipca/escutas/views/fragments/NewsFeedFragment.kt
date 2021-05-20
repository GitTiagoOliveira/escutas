package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news_feed.*
import pt.ipca.escutas.R
import pt.ipca.escutas.models.News
import pt.ipca.escutas.views.adapters.NewsFeedAdapter
import pt.ipca.escutas.views.adapters.OnNewItemClickListener


/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class NewsFeedFragment : Fragment(), OnNewItemClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    private var adapter: RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        layoutManager = LinearLayoutManager(this.context)

        val recycler: RecyclerView = view!!.findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager

        //adapter = NewsFeedAdapter()
        //recyclerView.adapter = adapter


    }

    public interface OnClickListener {
        fun onClick()
    }

}
