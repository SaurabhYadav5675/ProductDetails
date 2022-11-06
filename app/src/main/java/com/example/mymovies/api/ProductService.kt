package com.example.mymovies.api

import com.example.mymovies.model.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("products")
    suspend fun getProducts(): Response<ProductList>
}