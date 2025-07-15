package com.example.mvvmtest.mvvmhilt

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.mvvmtest.mvvmhilt.db.FakerDAO
import com.example.mvvmtest.mvvmhilt.db.FakerDB
import com.example.mvvmtest.mvvmhilt.models.Product
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class FakerDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)

    @Inject
    lateinit var fakerDB: FakerDB
    lateinit var fakerDAO: FakerDAO

    @Before
    fun setUp() {
        hiltAndroidRule.inject() // inject all the defined properties
        fakerDAO = fakerDB.getFakerDAO()
    }

    @Test
    fun insertProduct_returnsSingleProduct() = runTest {
        val product =  Product(
            category = "Electronics",
            description = "Latest smartphone with amazing features",
            id = 1,
            image = "https://example.com/images/phone.jpg",
            price = 699.99,
            title = "Smartphone X1"
        )

        fakerDAO.addProducts(listOf(product))
        val result = fakerDAO.getProducts()

        assertEquals(1, result.size)
    }

    @After
    fun closeDatabase() {
        fakerDB.close()
    }

}