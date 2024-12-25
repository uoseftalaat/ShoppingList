package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoppinglist.data.local.ShoppingItem
import com.example.shoppinglist.data.remote.responses.ImageResponse
import com.example.shoppinglist.util.Resource

class FakeTestShoppingRepo:ShoppingRepository {

    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val allItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)

    private val totalPrice = MutableLiveData<Float>()

    private var returnNetworkError = false

    fun setNetworkError(value:Boolean){
        returnNetworkError = value
    }

    private fun refreshLiveData(){
        allItems.postValue(shoppingItems)
        totalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice():Float{
        return shoppingItems.sumOf { it.price.toDouble() * it.amount }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun getAllItems(): LiveData<List<ShoppingItem>> {
        return allItems
    }

    override fun getTotalCost(): LiveData<Float> {
        return totalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(returnNetworkError){
            Resource.error("error",null)
        }else{
            Resource.success(ImageResponse(listOf(),0,0))
        }
    }
}