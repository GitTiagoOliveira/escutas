package pt.ipca.escutas.views.adapters

import android.graphics.Bitmap
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.GalleryController
import pt.ipca.escutas.models.Album
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.fragments.GalleryFragment

class GalleryFeedAdapter(
    var items: List<Album>,
    private var clickListener: OnAlbumFeedItemClickListener
) :
    RecyclerView.Adapter<GalleryFeedAdapter.GalleryFeedViewHolder>() {

    inner class GalleryFeedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The gallery feed controller.
         */
        private val galleryController: GalleryController = GalleryController()

        /**
         * The [image] represents the gallery card_image image.
         */
        var image: ImageView = itemView.findViewById(R.id.item_image)

        /**
         * The [textTitle] represents the gallery card_image title.
         */
        var textTitle: TextView = itemView.findViewById(R.id.item_title)

        /**
         * The [albumTitle] represents the gallery card_album title.
         */
        val albumTitle: TextView = textTitle

        /**
         * The [albumTitle] represents the gallery card_album image.
         */
        val AlbumImage: ImageView = image

        /**
         * Double click counter.
         */
        private var mLastClickTime: Long = 0

        /**
         * Populate the recycle viewer elements.
         */
        fun bind(item: Album, action: OnAlbumFeedItemClickListener) {

            albumTitle.text = item.name

            if (item.image != "") {
                galleryController.getImage(
                    item.image,
                    object : GenericCallback {
                        override fun onCallback(value: Any?) {
                            var image = value as Bitmap?
                            if (image != null) {
                                AlbumImage.setImageBitmap(image)
                            }
                        }
                    }
                )
            }

            itemView.setOnClickListener {
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000){
                    return@setOnClickListener
                }
                mLastClickTime = SystemClock.elapsedRealtime();
                action.onItemClick(item, absoluteAdapterPosition)
            }
        }
    }

    /**
     * Creates a new ViewHolder object for the RecyclerView
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_album, parent, false)
        return GalleryFeedViewHolder(view)
    }

    /**
     * Populate ViewHolders data
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: GalleryFeedViewHolder, position: Int) {
        when (holder) {
            else -> {
                holder.bind(items[position], clickListener)
            }
        }

        holder.itemView.setOnClickListener { view ->


            val bundle = Bundle()
            bundle.putString("eventName", items[position].name)

            val fragment = GalleryFragment()
            fragment.arguments = bundle
            val activity = view!!.context as AppCompatActivity
            val fragmentManager = activity.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.container, fragment)
            fragmentTransaction.addToBackStack(fragment.toString())
            fragmentTransaction.commit()
        }
    }

    /**
     * Retrieve item count
     *
     * @return items size
     */
    override fun getItemCount(): Int {
        return items.size
    }
}

interface OnAlbumFeedItemClickListener {
    /**
     * Action when opening an album.
     *
     * @param item
     * @param position
     */
    fun onItemClick(item: Album, position: Int) {
    }
}
