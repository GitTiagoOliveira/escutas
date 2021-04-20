package pt.ipca.escutas.views

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import pt.ipca.escutas.R

/**
 * Defines the news activity.
 *
 */
class NewsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val detailsText2 = getString(R.string.news_fulldetail)

        val textViewDetails: TextView = findViewById<TextView>(R.id.details_news)
        val textViewTitle: TextView = findViewById<TextView>(R.id.text_title)
        val textViewDesc: TextView = findViewById<TextView>(R.id.text_desc)
        val imageView: ImageView = findViewById<ImageView>(R.id.news_image)

        textViewDetails.text = detailsText2

        val intent = intent
        val tTitle = intent.getStringExtra("Title")
        val tDesc = intent.getStringExtra("Description")
        val tImage = intent.getIntExtra("Image", 0)

        textViewDesc.text = tDesc
        textViewTitle.text = tTitle
        imageView.setImageResource(tImage)
    }
}
