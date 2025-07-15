package com.example.mvvmtest.plainmvvm

import com.example.mvvmtest.plainmvvm.api.ProductsAPI
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsAPITest {

    lateinit var mockWebServer: MockWebServer
    lateinit var productAPI: ProductsAPI

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        productAPI = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductsAPI::class.java)
    }

    @Test
    fun testGetProducts() = runTest{
        val mockResponse = MockResponse()
        mockResponse.setBody("[]")
        mockWebServer.enqueue(mockResponse)

        val response = productAPI.getProducts()
        mockWebServer.takeRequest()

        assertEquals(true, response.body()!!.isEmpty())
    }

    @Test
    fun testGetProducts_returnProducts() = runTest{
        val mockResponse = MockResponse()

        val content = Helper.readFileResource("products.json")
        mockResponse.setResponseCode(200)
        mockResponse.setBody(content)
        mockWebServer.enqueue(mockResponse)

        val response = productAPI.getProducts()
        mockWebServer.takeRequest()

        assertEquals(false, response.body()!!.isEmpty())
        assertEquals(3, response.body()!!.size)
    }

    @Test
    fun testGetProducts_returnError() = runTest{
        val mockResponse = MockResponse()

        mockResponse.setResponseCode(404)
        mockResponse.setBody("Something went wrong")
        mockWebServer.enqueue(mockResponse)

        val response = productAPI.getProducts()
        mockWebServer.takeRequest()

        assertEquals(false, response.isSuccessful)
        assertEquals(404, response.code())
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}