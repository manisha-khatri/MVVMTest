package com.example.mvvmtest.mvvmhilt.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmtest.mvvmhilt.models.Product

@Database(entities = [Product::class], version = 1)
abstract class FakerDB : RoomDatabase() {
    abstract fun getFakerDAO(): FakerDAO
}
