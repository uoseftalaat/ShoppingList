package com.example.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.example.shoppinglist.data.local.ShoppingDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestModule {


    @Provides
    @Named("testing-dp")
    fun provideTestingDBInMemory(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context,ShoppingDB::class.java)
            .allowMainThreadQueries()
            .build()
}