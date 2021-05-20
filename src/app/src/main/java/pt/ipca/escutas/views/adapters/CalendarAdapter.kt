package pt.ipca.escutas.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.event_recyclerview.view.*
import pt.ipca.escutas.R
import pt.ipca.escutas.views.dataclasses.Events

class CalendarAdapter : RecyclerView.Adapter<CalendarAdapter.EventViewHolder>() {

    private var items : List<Events> = ArrayList()


    private val itemTitles = arrayOf(
        "Isto é um evento", "Isto quase que poderia ser um evento", "testezinho bonito numero 3",
        "mais um para aqui meu amigo", "estao aqui os cinco"
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
    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //var image: ImageView
        //var textTitle: TextView
        //var textDes: TextView

        val eventTitle: TextView = itemView.item_title
        val eventDetails: TextView = itemView.item_details
        val eventImage: ImageView = itemView.item_image
        val eventDay: TextView = itemView.event_day
        val eventMonth: TextView = itemView.event_month

        fun bind(events: Events) {
            eventTitle.setText(events.title)
            eventDetails.setText(events.details)
            Glide.with(itemView.context).load(events.image).into(eventImage)
            eventDay.setText(events.day)
            eventMonth.setText(events.month)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarAdapter.EventViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.event_recyclerview, parent, false)
        return EventViewHolder(v)
    }

    override fun onBindViewHolder(holder: CalendarAdapter.EventViewHolder, position: Int) {
        when (holder) {

            is EventViewHolder ->{
                holder.bind(items.get(position))
            }
        }
       // holder.textTitle.text = itemTitles [position]
       // holder.textDes.text = itemDetails [position]
       // holder.image.setImageResource(itemImages [position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(eventList: List<Events>) {
        items = eventList
    }
}
