package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
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

    /**
     * The event data list.
     */
    private var events: List<Album> = emptyList()

    /**
     * The gallery feed recycler view adapter.
     */
    private var adapter: RecyclerView.Adapter<GalleryFeedAdapter.GalleryFeedViewHolder>? = null

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.title = "Galeria"

        galleryController.getStoredEventsList(object : GenericCallback {
            override fun onCallback(value: Any?) {
                events = value as ArrayList<Album>
                initRecyclerView()
            }
        })
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gallery_feed, container, false)
    }

    /**
     * Populate recycler view with an adapter with all the necessary data.
     *
     */
    private fun initRecyclerView() {
        val recycler: RecyclerView = view!!.findViewById(R.id.recyclerViewFeed)

        recycler.layoutManager = GridLayoutManager(this.context, 2)
        recycler.isMotionEventSplittingEnabled = false;
        adapter = GalleryFeedAdapter(events, this)
        recyclerViewFeed.adapter = adapter
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
