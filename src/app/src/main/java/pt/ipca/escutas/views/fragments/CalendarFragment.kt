package pt.ipca.escutas.views.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.fragment_news_feed.*
import kotlinx.android.synthetic.main.fragment_news_feed.recyclerView
import pt.ipca.escutas.R
import pt.ipca.escutas.views.adapters.CalendarAdapter
import pt.ipca.escutas.views.dataclasses.Events

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class CalendarFragment : Fragment() {

    private lateinit var calendarAdapter: CalendarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecyclerView()
        addData()
    }


    private fun addData() {
        //val data =
        //calendarAdapter.submitList(data)
    }

    private fun initRecyclerView(){
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            calendarAdapter = CalendarAdapter()
            adapter = calendarAdapter
        }

    }

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<CalendarAdapter.EventViewHolder>? = null

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //layoutManager = LinearLayoutManager(this.context)

        //val recycler: RecyclerView = view!!.findViewById(R.id.recyclerView)
        //recycler.layoutManager = layoutManager

        //adapter = CalendarAdapter()
        //recyclerView.adapter = adapter

        val button: Button = view!!.findViewById(R.id.button_add)
        button.setOnClickListener {
            val fragment = AddEventFragment()
            val fragmentManager = activity!!.supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.container, fragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
    }

}
