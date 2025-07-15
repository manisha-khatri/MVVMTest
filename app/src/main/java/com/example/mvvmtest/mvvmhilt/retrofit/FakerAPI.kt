package com.example.mvvmtest.mvvmhilt.retrofit

import com.example.mvvmtest.mvvmhilt.models.Product
import retrofit2.Response
import retrofit2.http.GET

interface FakerAPI {

    @GET("products")
    suspend fun getProducts() : Response<List<Product>>
}