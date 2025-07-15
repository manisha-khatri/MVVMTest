package com.example.mvvmtest.plainmvvm.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvvmtest.plainmvvm.models.ProductListItem
import com.example.mvvmtest.plainmvvm.repository.ProductRepository
import com.example.mvvmtest.plainmvvm.utils.NetworkResult
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ProductRepository) : ViewModel() {

    private val _products = MutableLiveData<NetworkResult<List<ProductListItem>>>()
    val products: LiveData<NetworkResult<List<ProductListItem>>>
    get() = _products

    fun getProducts(){
        viewModelScope.launch {
            val result = repository.getProducts()
            _products.postValue(result)
        }
    }
}