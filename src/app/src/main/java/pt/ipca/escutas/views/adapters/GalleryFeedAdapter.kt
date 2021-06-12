package pt.ipca.escutas.views.adapters

import android.graphics.Bitmap
import android.os.Bundle
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

        var image: ImageView = itemView.findViewById(R.id.item_image)
        var textTitle: TextView = itemView.findViewById(R.id.item_title)

        val albumTitle: TextView = textTitle
        val AlbumImage: ImageView = image

        fun inititalize(item: Album, action: OnAlbumFeedItemClickListener) {

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
                action.onItemClick(item, absoluteAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryFeedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_album, parent, false)
        return GalleryFeedViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryFeedViewHolder, position: Int) {
        when (holder) {
            else -> {
                holder.inititalize(items[position], clickListener)
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

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(albumList: List<Album>) {
        items = albumList
    }
}

interface OnAlbumFeedItemClickListener {
    fun onItemClick(item: Album, position: Int) {
    }
}
