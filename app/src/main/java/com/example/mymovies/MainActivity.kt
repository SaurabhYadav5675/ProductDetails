package com.example.mymovies

import NoInternet
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovies.adapter.AdapterProductList
import com.example.mymovies.api.ProductService
import com.example.mymovies.api.RetrofitHelper
import com.example.mymovies.repository.ProductRepository
import com.example.mymovies.utilities.Utility
import com.example.mymovies.viewmodels.MainViewModel
import com.example.mymovies.viewmodels.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: AdapterProductList
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.productList)
        recyclerView!!.setHasFixedSize(true)

        val productService = RetrofitHelper.getInstance().create(ProductService::class.java)
        val repository = ProductRepository(productService)

        if (Utility.checkInternetConnection(this)) {
            GlobalScope.launch(Dispatchers.IO) { repository.getProducts() }
            mainViewModel = ViewModelProvider(
                this, MainViewModelFactory(repository)
            )[MainViewModel::class.java]

            setAdapterData();
        } else {
            val dialog =
                NoInternet(
                    this,
                    R.style.RoundCornerAlertDialog,
                    "Alert",
                    getString(R.string.no_internet)
                )
            dialog.show()
            dialog.onCloseClicked {
                dialog.dismiss();
            };
        }
    }

    private fun setAdapterData() {
        mainViewModel.products.observe(this, Observer { products ->
            if (products != null) {
                adapter = AdapterProductList(this, products.products)
                recyclerView.adapter = adapter
                val manager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                recyclerView.layoutManager = manager

                adapter.onItemClick = { data ->

                    val bundle = Bundle()
                    bundle.putSerializable("productInfo", data)
                    val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
                    intent.putExtras(bundle)
                    startActivity(intent)
                }
            } else {
                Toast.makeText(baseContext, "not getting data", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
