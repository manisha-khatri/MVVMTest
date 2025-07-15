package com.example.mvvmtest.mvvmhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvmtest.mvvmhilt.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.mvvmtest.R

@AndroidEntryPoint
class HiltMainActivity : AppCompatActivity() {

    lateinit var mainViewModel: MainViewModel

    private val products: TextView
    get() = findViewById(R.id.products)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hilt)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.productsLiveData.observe(this, Observer {
           products.text =  it.joinToString { x -> x.title + "\n\n" }
        })
    }

}



















