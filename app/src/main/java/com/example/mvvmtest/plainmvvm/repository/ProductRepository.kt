package com.example.mvvmtest.plainmvvm.repository

import com.example.mvvmtest.plainmvvm.api.ProductsAPI
import com.example.mvvmtest.plainmvvm.models.ProductListItem
import com.example.mvvmtest.plainmvvm.utils.NetworkResult

class ProductRepository(private val productsAPI: ProductsAPI) {

    suspend fun getProducts(): NetworkResult<List<ProductListItem>> {
        val response = productsAPI.getProducts()
        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                NetworkResult.Success(responseBody)
            } else {
                NetworkResult.Error("Something went wrong")
            }
        } else {
            NetworkResult.Error("Something went wrong")
        }
    }
}