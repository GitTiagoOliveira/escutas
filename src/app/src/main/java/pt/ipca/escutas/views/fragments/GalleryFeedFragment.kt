package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_gallery_feed.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.GalleryController
import pt.ipca.escutas.models.Album
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.adapters.GalleryFeedAdapter
import pt.ipca.escutas.views.adapters.OnAlbumFeedItemClickListener

/**
 * A Gallery [Fragment] subclass.
 * create an instance of this fragment.
 */
class GalleryFeedFragment : Fragment(), OnAlbumFeedItemClickListener {

    /**
     * The gallery controller.
     */
    private val galleryController: GalleryController = GalleryController()
    private var events: List<Album> = emptyList()
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<GalleryFeedAdapter.GalleryFeedViewHolder>? = null

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
    ): View? =
        inflater.inflate(R.layout.fragment_gallery_feed, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        galleryController.getStoredEventsList(object : GenericCallback {
            override fun onCallback(value: Any?) {
                events = value as ArrayList<Album>
                initRecyclerView()
            }
        })
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this.context)

        val recycler: RecyclerView = view!!.findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager

        recyclerView.apply {
            layoutManager = GridLayoutManager(this.context, 2)
        }

        adapter = GalleryFeedAdapter(events, this)
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Gets a instance of the current fragment.
         *
         * @return A instance of the current fragment.
         */
        fun getInstance(): GalleryFeedFragment = GalleryFeedFragment()
    }
}
