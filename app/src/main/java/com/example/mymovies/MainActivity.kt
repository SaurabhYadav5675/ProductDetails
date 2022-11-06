package com.example.mymovies

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
import com.example.mymovies.viewmodels.MainViewModel
import com.example.mymovies.viewmodels.MainViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: AdapterProductList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView: RecyclerView = findViewById(R.id.productList)
        recyclerView!!.setHasFixedSize(true)
        val manager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)


        val productService = RetrofitHelper.getInstance().create(ProductService::class.java)
        val repository = ProductRepository(productService)

        GlobalScope.launch(Dispatchers.IO) { repository.getProducts() }

        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(repository)).get(MainViewModel::class.java)

        mainViewModel.products.observe(this, Observer { products ->
            if (products != null) {
                adapter = AdapterProductList(this, products.products)
                recyclerView.adapter = adapter
                recyclerView.layoutManager = manager

                adapter.onItemClick = { contact ->

                    val bundle = Bundle()
                    bundle.putSerializable("productInfo", contact)
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
