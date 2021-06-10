package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_gallery.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.GalleryController
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.adapters.GalleryAdapter
import pt.ipca.escutas.views.adapters.OnNewItemClickListener

/**
 * Defines the gallery fragment.
 *
 */
class GalleryFragment : Fragment(), OnNewItemClickListener {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var images: List<String> = emptyList()
    private val galleryController: GalleryController = GalleryController()
    private var adapter: RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>? = null

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
        inflater.inflate(R.layout.fragment_gallery, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(this.context)

        val recycler: RecyclerView = view!!.findViewById(R.id.recyclerView)
        recycler.layoutManager = layoutManager

        adapter = GalleryAdapter(images)
        recyclerView.adapter = adapter
    }

    companion object {
        /**
         * Gets a instance of the current fragment.
         *
         * @return A instance of the current fragment.
         */
        fun getInstance(): GalleryFragment = GalleryFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var eventName = arguments?.getString("eventName", "DefaultEmptyPath");

        galleryController.getImagesPath(
            eventName!!.trimEnd(),
            object : GenericCallback {
                override fun onCallback(value: Any?) {
                    images = value as ArrayList<String>
                    initRecyclerView()
                }
            }
        )
    }
}
