package com.example.mymovies.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovies.api.ProductService
import com.example.mymovies.model.ProductList

class ProductRepository(private val productService: ProductService) {

    private val proLiveData = MutableLiveData<ProductList>()

    val products: LiveData<ProductList>
        get() = proLiveData

    suspend fun getProducts() {
        val result = productService.getProducts()
        if (result.body() != null) {
            proLiveData.postValue(result.body())
        }
    }
}