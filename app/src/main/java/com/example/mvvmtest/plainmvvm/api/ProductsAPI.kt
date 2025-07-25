package com.example.mvvmtest.plainmvvm.api

import com.example.mvvmtest.plainmvvm.models.ProductListItem
import retrofit2.Response
import retrofit2.http.GET

interface ProductsAPI {

    @GET("/products")
    suspend fun getProducts() : Response<List<ProductListItem>>

}