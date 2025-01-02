package com.example.shoppinglist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.androiddevs.shoppinglisttestingyt.other.Event
import com.example.shoppinglist.data.local.ShoppingItem
import com.example.shoppinglist.data.remote.responses.ImageResponse
import com.example.shoppinglist.data.repository.ShoppingRepository
import com.example.shoppinglist.util.Constants
import com.example.shoppinglist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repo: ShoppingRepository
):ViewModel() {

    val shoppingItems = repo.getAllItems()

    val totalPrice = repo.getTotalCost()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val images: LiveData<Event<Resource<ImageResponse>>> = _images

    private val _curImageUrl = MutableLiveData<String>()
    val curImageUrl: LiveData<String> = _curImageUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus: LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url:String){
        _curImageUrl.postValue(url)
    }

    fun deleteShoppingItem(value:ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteShoppingItem(value)
    }

    fun insertShoppingItemIntoDB(value:ShoppingItem) = viewModelScope.launch(Dispatchers.IO) {
        repo.insertShoppingItem(value)
    }

    fun insertShoppingItem(name:String,amount:String,price:String){
        if(name.isEmpty() || amount.isEmpty() || price.isEmpty()){
            _insertShoppingItemStatus.postValue(
                Event(Resource.error(
                    "one of the attributes is empty",
                    null
                ))
            )
            return
        }
        if(name.length > Constants.MAX_NAME_LENGTH){
            _insertShoppingItemStatus.postValue(
                Event(Resource.error(
                    "the name of the item must not exceed ${Constants.MAX_NAME_LENGTH}",
                    null
                ))
            )
            return
        }
        if(price.length > Constants.MAX_PRICE_LENGTH){
            _insertShoppingItemStatus.postValue(
                Event(Resource.error(
                    "the price of the item must not exceed ${Constants.MAX_PRICE_LENGTH}",
                    null
                ))
            )
            return
        }
        val numericAmount = try {
            amount.toInt()
        }catch (ex: Exception){
            _insertShoppingItemStatus.postValue(
                Event(Resource.error(
                    "the amount is wrongly entered",
                    null
                ))
            )
            return
        }
        val numericPrice = try {
            price.toFloat()
        }catch (ex: Exception){
            _insertShoppingItemStatus.postValue(
                Event(Resource.error(
                    "the price is not set properly",
                    null
                ))
            )
            return
        }
        val shoppingItem = ShoppingItem(name,numericAmount,numericPrice,_curImageUrl.value ?: "")
        insertShoppingItemIntoDB(shoppingItem)
        setCurImageUrl("")
        _insertShoppingItemStatus.postValue(
            Event(Resource.success(
                shoppingItem
            ))
        )
    }

    fun searchImage(imageQuery:String){
        if (imageQuery.isEmpty()){
            return
        }
        _images.value = Event(Resource.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            val response = repo.searchForImage(imageQuery)
            _images.value = Event(response)
        }
    }

}

class ShoppingViewModelFactory(
    private val repo: ShoppingRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(repo) as T
    }
}