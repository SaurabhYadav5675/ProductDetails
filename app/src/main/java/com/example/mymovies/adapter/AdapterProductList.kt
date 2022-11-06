package com.example.mymovies.adapter

import android.content.Context
import android.os.Build
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.R

import com.example.mymovies.model.Product
import com.squareup.picasso.Picasso

class AdapterProductList(var context: Context, var productList: List<Product>) :
    RecyclerView.Adapter<AdapterProductList.ViewHolder>() {

    var onItemClick: ((Product) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(context).inflate(R.layout.adapter_product_list, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.productName.text = productList[position].category
        holder.productDes.text = productList[position].description

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.productPrice.text =
                Html.fromHtml(
                    "<font color='red' size='20px'><big>&#8377 ${productList[position].price}</big></font>  (${productList[position].discountPercentage.toString()}% off)",
                    Html.FROM_HTML_MODE_COMPACT
                );
        } else {
            holder.productPrice.text =
                Html.fromHtml("<font color='red' size='20px'><big>&#8377 ${productList[position].price}</big></font>   (${productList[position].discountPercentage.toString()}% off)")
        }
        Picasso.get().load(productList[position].thumbnail).into(holder.productImg)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    inner class ViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {
        var productImg: ImageView
        var productName: TextView
        var productDes: TextView
        var productPrice: TextView

        init {
            productImg = view.findViewById(R.id.productThumb)
            productName = view.findViewById(R.id.productName)
            productDes = view.findViewById(R.id.productDescription)
            productPrice = view.findViewById(R.id.productPrice)

            view.setOnClickListener {
                onItemClick?.invoke(productList[adapterPosition])
            }
        }
    }
}