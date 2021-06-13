package pt.ipca.escutas.views.adapters

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_event.view.event_day
import kotlinx.android.synthetic.main.card_event.view.event_month
import kotlinx.android.synthetic.main.card_event.view.item_details
import kotlinx.android.synthetic.main.card_event.view.item_image
import kotlinx.android.synthetic.main.card_event.view.item_title
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.CalendarController
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.utils.DateUtils

/**
 * The calendar adapter for Calendar Fragment.
 */
class CalendarAdapter(var items: List<Event>) : RecyclerView.Adapter<CalendarAdapter.EventViewHolder>() {

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /**
         * The calendar controller.
         */
        private val calendarController: CalendarController = CalendarController()

        /**
         * The [eventTitle] represents the calendar card_event title.
         */
        val eventTitle: TextView = itemView.item_title

        /**
         * The [eventDetails] represents the calendar card_event details.
         */
        val eventDetails: TextView = itemView.item_details

        /**
         * The [eventImage] represents the calendar card_event image.
         */
        val eventImage: ImageView = itemView.item_image

        /**
         * The [eventDay] represents the calendar card_event day.
         */
        val eventDay: TextView = itemView.event_day

        /**
         * The [eventMonth] represents the calendar card_event month.
         */
        val eventMonth: TextView = itemView.event_month

        /**
         * Populate the recycle viewer elements.
         */
        fun bind(events: Event) {
            eventTitle.text = events.name
            eventDetails.text = events.description
            val month = DateUtils.getMonth(events.startDate)
            val day = DateUtils.getDay(events.startDate)
            eventDay.text = day
            eventMonth.text = month

            if (events.attachment != null && events.attachment != "") {
                calendarController.getEventImage(
                    events.attachment,
                    object : GenericCallback {
                        override fun onCallback(value: Any?) {
                            var image = value as Bitmap
                            if (image != null) {
                                eventImage.setImageBitmap(image)
                            }
                        }
                    }
                )
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_event, parent, false)
        return EventViewHolder(v)
    }

    /**
     * Populate ViewHolders data
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        when (holder) {

            is EventViewHolder -> {
                holder.bind(items[position])
            }
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
