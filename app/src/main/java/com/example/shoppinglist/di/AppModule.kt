package com.example.shoppinglist.di

import android.content.Context
import androidx.room.Room
import com.example.shoppinglist.data.local.ShoppingDB
import com.example.shoppinglist.data.local.ShoppingDao
import com.example.shoppinglist.data.remote.PixabayAPI
import com.example.shoppinglist.data.repository.DefaultRepository
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.util.Constants.BASE_URL
import com.example.shoppinglist.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,ShoppingDB::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        shoppingDatabase:ShoppingDB
    ) = shoppingDatabase.shoppingDao()

    @Singleton
    @Provides
    fun providePixabayApi() = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .build()
        .create(PixabayAPI::class.java)


    @Singleton
    @Provides
    fun provideDefaultRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ) = DefaultRepository(dao,api) as ShoppingRepository

}