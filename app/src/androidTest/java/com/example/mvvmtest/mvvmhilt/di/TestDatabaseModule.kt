package com.example.mvvmtest.mvvmhilt.di

import android.content.Context
import androidx.room.Room
import com.example.mvvmtest.mvvmhilt.db.FakerDB
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@TestInstallIn(components = [SingletonComponent::class], replaces = [DatabaseModule::class]) // Replaces DatabaseModule with TestDatabaseModule
@Module
class TestDatabaseModule {

    @Singleton
    @Provides
    fun provideTestDatabase(@ApplicationContext context: Context): FakerDB {
        return Room.inMemoryDatabaseBuilder(
            context,
            FakerDB::class.java
        ).allowMainThreadQueries().build()
    }
}