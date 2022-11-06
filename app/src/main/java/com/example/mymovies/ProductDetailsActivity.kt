package com.example.mymovies

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.mymovies.model.Product

class ProductDetailsActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)
        val products = intent.getSerializableExtra("productInfo") as Product;
        var imgList = ArrayList<SlideModel>()
        var imgUrl = ArrayList<String>()
        imgUrl.addAll(products.images)

        var proTitle = findViewById<TextView>(R.id.proTitle)
        var proRating = findViewById<TextView>(R.id.proRating)
        var proPrice = findViewById<TextView>(R.id.proPrice)
        var proDescription = findViewById<TextView>(R.id.proDescription)
        var imgSlider = findViewById<ImageSlider>(R.id.imgSlider)

        for (images in imgUrl) {
            imgList.add(SlideModel(images, ""))
        }
        imgSlider.setImageList(imgList, ScaleTypes.FIT)

        proTitle.text = products.title
        proRating.text = products.rating.toString()
        proDescription.text = products.description

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            proPrice.text =
                Html.fromHtml(
                    "<font color='red' size='20px'><big>&#8377 ${products.price}</big></font>",
                    Html.FROM_HTML_MODE_COMPACT
                );
        } else {
            proPrice.text =
                Html.fromHtml("<font color='red' size='20px'><big>&#8377 ${products.price}</big></font>")
        }
    }
}