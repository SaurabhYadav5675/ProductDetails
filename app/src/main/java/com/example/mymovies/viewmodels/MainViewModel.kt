package com.example.mymovies.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymovies.model.ProductList
import com.example.mymovies.repository.ProductRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

 /*   init {
        viewModelScope.launch(Dispatchers.IO) { repository.getProducts() }
    }*/

    val products: LiveData<ProductList>
        get() = repository.products
}