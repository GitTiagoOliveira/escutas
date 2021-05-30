package pt.ipca.escutas.views.adapters

import android.graphics.Bitmap
import android.provider.CalendarContract
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
import pt.ipca.escutas.controllers.CalendarController
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.models.News
import pt.ipca.escutas.services.callbacks.StorageCallback
import pt.ipca.escutas.utils.DateUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class CalendarAdapter(var items: List<Event>) : RecyclerView.Adapter<CalendarAdapter.EventViewHolder>() {



    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The calendar controller.
         */
        private val calendarController: CalendarController = CalendarController()

        val eventTitle: TextView = itemView.item_title
        val eventDetails: TextView = itemView.item_details
        val eventImage: ImageView = itemView.item_image
        val eventDay: TextView = itemView.event_day
        val eventMonth: TextView = itemView.event_month

        fun bind(events: Event) {
            eventTitle.setText(events.name)
            eventDetails.setText(events.description)
            //Glide.with(itemView.context).load(events.attachment).into(eventImage)
            val month = DateUtils.getMonth(events.startDate)
            val day = DateUtils.getDay(events.startDate)
            eventDay.setText(day)
            eventMonth.setText(month)

            if(events.attachment != null && events.attachment != ""){
                calendarController.getEventImage(events.attachment, object : StorageCallback {
                    override fun onCallback(image: Bitmap?) {
                        if (image != null) {
                            eventImage.setImageBitmap(image)
                        };
                    }
                })
            }
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

    fun submitList(eventList: List<Event>) {
        items = eventList
    }
}
