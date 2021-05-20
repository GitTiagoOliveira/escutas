package pt.ipca.escutas.views.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import pt.ipca.escutas.R

/**
 * A simple [Fragment] subclass.
 * create an instance of this fragment.
 */
class NewsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val detailsText2 = getString(R.string.news_fulldetail)

        val textViewDetails: TextView = view!!.findViewById(R.id.details_news)
        val textViewTitle: TextView = view!!.findViewById(R.id.text_title)
        val textViewDesc: TextView = view!!.findViewById(R.id.text_desc)
        val imageView: ImageView = view!!.findViewById(R.id.news_image)

        textViewDetails.text = detailsText2

        val intent = Intent()
        val tTitle = intent.getStringExtra("Title")
        val tDesc = intent.getStringExtra("Description")
        val tImage = intent.getIntExtra("Image", 0)

        textViewDesc.text = tDesc
        textViewTitle.text = tTitle
        imageView.setImageResource(tImage)
    }
}
