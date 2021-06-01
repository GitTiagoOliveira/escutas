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
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.event_recyclerview.view.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.NewsFeedController
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.StorageCallback
import pt.ipca.escutas.views.fragments.NewsFragment


class NewsFeedAdapter(var items: List<News>, var clickListener: OnNewItemClickListener) : RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>() {


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The news feed controller.
         */
        private val newsfeedController: NewsFeedController = NewsFeedController()

        var image: ImageView = itemView.findViewById(R.id.item_image)
        var textTitle: TextView = itemView.findViewById(R.id.item_title)
        var textDes: TextView = itemView.findViewById(R.id.item_details)

        val newsTitle: TextView = textTitle
        val newsDetails: TextView = textDes
        val newsImage: ImageView = image

        fun inititalize(item: News, action: OnNewItemClickListener) {
            newsTitle.setText(item.title)
            newsDetails.setText(item.body)

            if(item.image != null && item.image != ""){
                newsfeedController.getNewsImage(item.image, object : StorageCallback {
                    override fun onCallback(image: Bitmap?) {
                        if (image != null) {
                            newsImage.setImageBitmap(image)
                        };
                    }
                })
            }

            itemView.setOnClickListener{
                action.onItemClick(item, absoluteAdapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_news, parent, false)
        return NewsViewHolder(v)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {


        when (holder) {

            is NewsFeedAdapter.NewsViewHolder -> {
                holder.inititalize(items.get(position), clickListener)
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


    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(newsList: List<News>) {
        items = newsList
    }

}


interface OnNewItemClickListener{
    fun onItemClick(item: News, position: Int) {

    }
}

