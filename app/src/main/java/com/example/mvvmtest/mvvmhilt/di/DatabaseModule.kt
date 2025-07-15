package com.example.mvvmtest.mvvmhilt.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmtest.mvvmhilt.db.FakerDAO
import com.example.mvvmtest.mvvmhilt.db.FakerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideFakerDB(@ApplicationContext context: Context): FakerDB {
        return Room.databaseBuilder(context, FakerDB::class.java, "FakerDB").build()
    }

    @Singleton
    @Provides
    fun provideFakerDAO(db: FakerDB): FakerDAO {
        return db.getFakerDAO()
    }
}
