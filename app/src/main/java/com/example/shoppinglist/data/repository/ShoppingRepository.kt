package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.local.ShoppingItem
import com.example.shoppinglist.data.remote.responses.ImageResponse
import com.example.shoppinglist.util.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun getAllItems():LiveData<List<ShoppingItem>>

    fun getTotalCost():LiveData<Float>

    suspend fun searchForImage(imageQuery:String):Resource<ImageResponse>
}