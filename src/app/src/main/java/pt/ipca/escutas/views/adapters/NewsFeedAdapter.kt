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
import pt.ipca.escutas.controllers.NewsFeedController
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.fragments.NewsFragment

class NewsFeedAdapter(var items: List<News>, var clickListener: OnNewItemClickListener) : RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The news feed controller.
         */
        private val newsfeedController: NewsFeedController = NewsFeedController()

        /**
         * The [image] represents the news feed card_news image.
         */
        var image: ImageView = itemView.findViewById(R.id.item_image)

        /**
         * The [textTitle] represents the news feed card_news title.
         */
        var textTitle: TextView = itemView.findViewById(R.id.item_title)

        /**
         * The [textDes] represents the news feed card_news description.
         */
        var textDes: TextView = itemView.findViewById(R.id.item_details)

        /**
         * The [newsTitle]  represents the news card_news title.
         */
        val newsTitle: TextView = textTitle

        /**
         * The [newsDetails] represents the news card_news details.
         */
        val newsDetails: TextView = textDes

        /**
         * The [newsImage] represents the news card_news image.
         */
        val newsImage: ImageView = image

        /**
         * Populate the recycle viewer elements.
         */
        fun bind(item: News, action: OnNewItemClickListener) {
            newsTitle.text = item.title
            newsDetails.text = item.body

            if (item.image != null && item.image != "") {
                newsfeedController.getNewsImage(
                    item.image,
                    object : GenericCallback {
                        override fun onCallback(value: Any?) {
                            var image = value as Bitmap
                            if (image != null) {
                                newsImage.setImageBitmap(image)
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

    /**
     * Creates a new ViewHolder object for the RecyclerView
     *
     * @param parent
     * @param viewType
     * @return
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_news, parent, false)
        return NewsViewHolder(v)
    }

    /**
     * Populate ViewHolders data
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        when (holder) {

            is NewsFeedAdapter.NewsViewHolder -> {
                holder.bind(items[position], clickListener)
            }
        }

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val bundle = Bundle()
                bundle.putString("title", items[position].title)
                bundle.putString("body", items[position].body)
                bundle.putString("image", items[position].image)
                bundle.putString("details", items[position].details)

                val fragment = NewsFragment()
                fragment.arguments = bundle
                val activity = v!!.context as AppCompatActivity
                val fragmentManager = activity!!.supportFragmentManager
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(R.id.container, fragment)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
            }
        })
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

interface OnNewItemClickListener {

    /**
     * Action when opening a news.
     *
     * @param item
     * @param position
     */
    fun onItemClick(item: News, position: Int) {
    }
}
