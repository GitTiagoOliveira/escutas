package pt.ipca.escutas.views.fragments

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_news.*
import pt.ipca.escutas.R
import pt.ipca.escutas.controllers.NewsFeedController
import pt.ipca.escutas.services.callbacks.GenericCallback


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

        val newsfeedController: NewsFeedController = NewsFeedController()

        val textViewDetails: TextView = view!!.findViewById(R.id.details_news)
        val textViewTitle: TextView = view!!.findViewById(R.id.text_title)
        val textViewDesc: TextView = view!!.findViewById(R.id.text_desc)
        val imageView: ImageView = view!!.findViewById(R.id.news_image)


        val tTitle = arguments?.getString("title")
        val tDesc = arguments?.getString("body")
        val tImage = arguments?.getString("image")
        val tDetails = arguments?.getString("details")


        if(tImage != null && tImage != ""){
            newsfeedController.getNewsImage(tImage, object : GenericCallback {
                override fun onCallback(value: Any?) {
                    var image = value as Bitmap
                    if (image != null) {
                        imageView.setImageBitmap(image)
                    };
                }
            })
        }


        textViewDesc.text = tDesc
        textViewTitle.text = tTitle
        textViewDetails.text = tDetails

        val button: ImageButton = view!!.findViewById(R.id.back_button)
        button.setOnClickListener {
            fragmentManager?.popBackStack()
        }

    }
}
