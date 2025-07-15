package com.example.mvvmtest.mvvmhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.example.mvvmtest.mvvmhilt.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.mvvmtest.R

@AndroidEntryPoint
class HiltMainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val products: TextView
        get() = findViewById(R.id.products)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_hilt)

        mainViewModel.productsLiveData.observe(this) { productList ->
            products.text = productList.joinToString("\n\n") { it.title }
        }
    }
}


















