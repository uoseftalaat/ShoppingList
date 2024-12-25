package com.example.shoppinglist.data.repository

import androidx.lifecycle.LiveData
import com.example.shoppinglist.data.local.ShoppingDao
import com.example.shoppinglist.data.local.ShoppingItem
import com.example.shoppinglist.data.remote.PixabayAPI
import com.example.shoppinglist.data.remote.responses.ImageResponse
import com.example.shoppinglist.util.Resource
import retrofit2.Response
import javax.inject.Inject

class DefaultRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI
):ShoppingRepository
{
    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun getAllItems(): LiveData<List<ShoppingItem>> = shoppingDao.getAllItems()

    override fun getTotalCost(): LiveData<Float> = shoppingDao.getTotalPrice()

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("unknown error",null)
            }
            else{
                Resource.error("An Unknown error occurred ",null)
            }
        }catch (ex:Exception){
            return Resource.error("couldn't reach for the data please check you connection",null)
        }
    }

}