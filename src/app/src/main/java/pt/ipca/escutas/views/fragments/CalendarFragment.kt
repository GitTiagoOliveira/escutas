package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news_feed.recyclerView
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.CalendarController
import pt.ipca.escutas.models.Event
import pt.ipca.escutas.services.callbacks.GenericCallback
import pt.ipca.escutas.views.adapters.CalendarAdapter
import java.util.ArrayList

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {

    /**
     * The calendar adapter for the calendar recycler view fragment.
     */
    private lateinit var calendarAdapter: CalendarAdapter

    /**
     * The calendar controller.
     */
    private val calendarController: CalendarController = CalendarController()

    /**
     * The list of events.
     */
    private var events: List<Event> = emptyList()

    /**
     * Invoked when the activity is starting.
     *
     * @param savedInstanceState The saved instance state.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val applicationContext = activity!!.applicationContext

        calendarController.getStoredEventsList(
            applicationContext,
            object : GenericCallback {
                override fun onCallback(value: Any?) {
                    if (value != null) {
                        var list = value as ArrayList<Event>
                        events = list
                        initRecyclerView()
                    }
                }
            }
        )
    }

    /**
     * Populate recycler view with an adapter with all the necessary data.
     *
     */
    private fun initRecyclerView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            calendarAdapter = CalendarAdapter(events)
            adapter = calendarAdapter
        }
    }

    /**
     * Invoked when the fragment instantiates his view.
     *
     * @param inflater The inflater.
     * @param container The container.
     * @param savedInstanceState The saved instance state.
     * @return The fragment view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_calendar, container, false)
    }

    companion object {
        /**
         * Gets a instance of the current fragment.
         *
         * @return A instance of the current fragment.
         */
        fun getInstance(): CalendarFragment = CalendarFragment()
    }

    /**
     * Invoked when the view is fully created.
     *
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button: Button = view!!.findViewById(R.id.button_add)
        button.setOnClickListener {
            val fragment = AddEventFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.add(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }
}
