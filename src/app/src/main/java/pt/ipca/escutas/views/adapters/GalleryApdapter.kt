package pt.ipca.escutas.views.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.GalleryController
import pt.ipca.escutas.services.callbacks.StorageCallback

class GalleryAdapter(var images: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The gallery controller.
         */
        private val galleryController: GalleryController = GalleryController()

        var image: ImageView = itemView.findViewById(R.id.item_image)
        var textTitle: TextView = itemView.findViewById(R.id.item_title)

        val albumTitle: TextView = textTitle
        val AlbumImage: ImageView = image

        fun inititalize(item: String) {

            albumTitle.text = item

            if (item != "") galleryController.getImage(item, object : StorageCallback {
                override fun onCallback(image: Bitmap?) {
                    if (image != null) {
                        AlbumImage.setImageBitmap(image)
                    };
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_image, parent, false)
        return GalleryViewHolder(v)
    }

    override fun onBindViewHolder(holder: GalleryAdapter.GalleryViewHolder, position: Int) {
        when (holder) {
            else -> {
                holder.inititalize(images[position])
            }
        }
    }

    override fun getItemCount(): Int = images.size

    fun submitList(imagesList: List<String>) {
        images = imagesList
    }
}
