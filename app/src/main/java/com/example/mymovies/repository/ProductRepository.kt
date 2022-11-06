package com.example.mymovies.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovies.api.ProductService
import com.example.mymovies.model.ProductList

class ProductRepository(private val productService: ProductService) {

    private val proLiveData = MutableLiveData<ProductList>()

    val products: LiveData<ProductList>
        get() = proLiveData

    suspend fun getProducts() {
        Log.e("Data1", "getting from api")
        val result = productService.getProducts()
        if (result.body() != null) {
            Log.e("Data122", result.body().toString())
            proLiveData.postValue(result.body())
        }
    }
}