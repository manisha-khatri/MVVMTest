package com.example.mvvmtest.plainmvvm


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmtest.R
import com.example.mvvmtest.plainmvvm.adapter.ProductAdapter
import com.example.mvvmtest.plainmvvm.utils.NetworkResult
import com.example.mvvmtest.plainmvvm.viewmodels.MainViewModel
import com.example.mvvmtest.plainmvvm.viewmodels.MainViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var adapter : ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.productList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val repository = (application as StoreApplication).productRepository
        mainViewModel = ViewModelProvider(this, MainViewModelFactory(repository))
            .get(MainViewModel::class.java)

        mainViewModel.getProducts()

        mainViewModel.products.observe(this, Observer {
            when(it){
                is NetworkResult.Success -> {
                    Log.d("CHEEZ", it.data.toString())
                    adapter = ProductAdapter(it.data!!)
                    recyclerView.adapter = adapter

                }
                is NetworkResult.Error -> {}
                is NetworkResult.Loading<*> -> TODO()
            }
        })
    }
}