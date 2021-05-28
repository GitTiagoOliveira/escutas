package pt.ipca.escutas.views.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.event_recyclerview.view.*
import pt.ipca.escutas.R
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.News
import pt.ipca.escutas.views.fragments.NewsFeedFragment
import pt.ipca.escutas.views.fragments.NewsFragment


class NewsFeedAdapter(var items: List<News>, var clickListener: OnNewItemClickListener) : RecyclerView.Adapter<NewsFeedAdapter.NewsViewHolder>() {


    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.item_image)
        var textTitle: TextView = itemView.findViewById(R.id.item_title)
        var textDes: TextView = itemView.findViewById(R.id.item_details)

        val newsTitle: TextView = textTitle
        val newsDetails: TextView = textDes
        val newsImage: ImageView = image

        fun inititalize(item: News, action:OnNewItemClickListener) {
            newsTitle.setText(item.title)
            newsDetails.setText(item.body)
            Glide.with(itemView.context).load(item.image).into(newsImage)

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

            is NewsFeedAdapter.NewsViewHolder ->{
                holder.inititalize(items.get(position), clickListener)
            }
        }

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

