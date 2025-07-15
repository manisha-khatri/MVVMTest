package com.example.mvvmtest.plainmvvm.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mvvmtest.plainmvvm.getOrAwaitValue
import com.example.mvvmtest.plainmvvm.repository.ProductRepository
import com.example.mvvmtest.plainmvvm.utils.NetworkResult
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    private var testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var repository: ProductRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun test_GetProducts() = runTest {
        Mockito.`when`(repository.getProducts()).thenReturn(NetworkResult.Success(emptyList()))

        val sut = MainViewModel(repository)
        sut.getProducts()

        testDispatcher.scheduler.advanceUntilIdle()

        val result = sut.products.getOrAwaitValue()
        assertEquals(0, result.data!!.size)
    }

    @Test
    fun test_GetProducts_expectedError() = runTest {
        val errMsg = "Something went wrong!!"
        Mockito.`when`(repository.getProducts()).thenReturn(NetworkResult.Error(errMsg))

        val sut = MainViewModel(repository)
        sut.getProducts()

        testDispatcher.scheduler.advanceUntilIdle()

        val result = sut.products.getOrAwaitValue() // system under test

        assertEquals(true, result is NetworkResult.Error)
        assertEquals(errMsg, result.message)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}