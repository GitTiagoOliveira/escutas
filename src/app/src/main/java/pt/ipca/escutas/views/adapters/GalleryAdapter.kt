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
import pt.ipca.escutas.services.callbacks.GenericCallback

/**
 * The gallery adapter for Gallery Fragment.
 */
class GalleryAdapter(var images: List<String>) :
    RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The gallery controller.
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
         * Populate the recycle viewer elements.
         */
        fun bind(item: String) {

            albumTitle.text = item.substringAfterLast("/").substringBeforeLast(".")

            if (item != "") galleryController.getImage(
                item,
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
    }

    /**
     * Creates a new ViewHolder object for the RecyclerView
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_image, parent, false)
        return GalleryViewHolder(v)
    }

    /**
     * Populate ViewHolders data
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: GalleryAdapter.GalleryViewHolder, position: Int) {
        when (holder) {
            else -> {
                holder.bind(images[position])
            }
        }
    }

    /**
     * Retrieve item count
     *
     * @return items size
     */
    override fun getItemCount(): Int {
        return images.size
    }
}
