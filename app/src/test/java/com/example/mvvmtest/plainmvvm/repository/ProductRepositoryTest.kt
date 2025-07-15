package com.example.mvvmtest.plainmvvm.repository

import com.example.mvvmtest.plainmvvm.api.ProductsAPI
import com.example.mvvmtest.plainmvvm.models.ProductListItem
import com.example.mvvmtest.plainmvvm.utils.NetworkResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

class ProductRepositoryTest {

    @Mock
    lateinit var productsAPI: ProductsAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun testGetProducts_EmptyList() = runTest {
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(emptyList()))

        val sut = ProductRepository(productsAPI) //system under test
        val result = sut.getProducts()

        assertEquals(true, result is NetworkResult.Success)
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun testGetProducts_expectedProductList() = runTest {
        val fakeList = fakeProductList()
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.success(fakeList))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()

        assertEquals(true, result is NetworkResult.Success)
        assertEquals(5, result.data!!.size)
        assertEquals("Smartphone X1", (result as NetworkResult.Success).data?.get(0)?.title)
    }

    @Test
    fun testGetProducts_expectedError() = runTest {
        Mockito.`when`(productsAPI.getProducts()).thenReturn(Response.error(401, "Unauthorized".toResponseBody()))

        val sut = ProductRepository(productsAPI)
        val result = sut.getProducts()

        assertEquals(true, result is NetworkResult.Error)
        assertEquals("Something went wrong", result.message)

    }



    fun fakeProductList() = listOf<ProductListItem>(
            ProductListItem(
                category = "Electronics",
                description = "Latest smartphone with amazing features",
                id = 1,
                image = "https://example.com/images/phone.jpg",
                price = 699.99,
                title = "Smartphone X1"
            ),
            ProductListItem(
                category = "Books",
                description = "An inspiring self-help book for personal growth",
                id = 2,
                image = "https://example.com/images/book.jpg",
                price = 14.99,
                title = "Unleash the Giant Within"
            ),
            ProductListItem(
                category = "Fashion",
                description = "Trendy unisex sunglasses with UV protection",
                id = 3,
                image = "https://example.com/images/sunglasses.jpg",
                price = 29.99,
                title = "Sunglasses Aviator Classic"
            ),
            ProductListItem(
                category = "Home",
                description = "Minimalist LED desk lamp with touch control",
                id = 4,
                image = "https://example.com/images/lamp.jpg",
                price = 39.99,
                title = "LED Touch Desk Lamp"
            ),
            ProductListItem(
                category = "Fitness",
                description = "Durable and lightweight yoga mat for all levels",
                id = 5,
                image = "https://example.com/images/yogamat.jpg",
                price = 24.99,
                title = "Eco-Friendly Yoga Mat"
            )
        )
}

