package pt.ipca.escutas.views.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipca.escutas.R
import pt.ipca.escutas.views.dataclasses.News
import pt.ipca.escutas.views.fragments.NewsFeedFragment
import pt.ipca.escutas.views.fragments.NewsFragment


class NewsFeedAdapter(var items: ArrayList<News>, var clickListener: OnNewItemClickListener) : RecyclerView.Adapter<NewsFeedAdapter.ViewHolder>() {

    private val itemTitles = arrayOf(
            "Isto é uma noticia",
            "Isto quase que poderia ser uma noticia",
            "testezinho bonito numero 3",
            "mais um para aqui meu amigo",
            "estao aqui as cinco"
    )

    private val itemDetails = arrayOf(
            "detalhes numero 1", "detalhes numero 2", "detalhes numero 3", "detalhes numero 4",
            "detalhes numero 5"
    )

    private val itemImages = intArrayOf(
            R.drawable.ic_vector_gallery_heart,
            R.drawable.ic_vector_gallery_heart_full,
            R.drawable.ic_vector_logo,
            R.drawable.ic_vector_logo_alt,
            R.drawable.ic_vector_social_facebook
    )

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView = itemView.findViewById(R.id.item_image)
        var textTitle: TextView = itemView.findViewById(R.id.item_title)
        var textDes: TextView = itemView.findViewById(R.id.item_details)

        fun inititalize(item: News, action:OnNewItemClickListener) {
            textDes.text = item.description
            textTitle.text = item.title
            image.setImageResource(item.image)

            itemView.setOnClickListener{
                action.onItemClick(item, absoluteAdapterPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_news, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textTitle.text = itemTitles[position]
        holder.textDes.text = itemDetails[position]
        holder.image.setImageResource(itemImages[position])

        holder.inititalize(items.get(position), clickListener)

        holder.itemView.setOnClickListener { v: View ->

            val intent = Intent(v.context, NewsFragment::class.java)
            intent.putExtra("Title", itemTitles[position])
            intent.putExtra("Description", itemDetails[position])
            intent.putExtra("Image", itemImages[position])

            v.context.startActivity(intent)
        }

    }


    override fun getItemCount(): Int {
        return itemTitles.size
    }

}


interface OnNewItemClickListener{
    fun onItemClick(item: News, position: Int) {

    }
}

