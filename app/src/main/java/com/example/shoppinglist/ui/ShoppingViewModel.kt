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
import com.example.shoppinglist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repo: ShoppingRepository
):ViewModel() {

    val shoppingItem = repo.getAllItems()

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

    }

    fun searchImage(imageQuery:String){

    }

}

class ShoppingViewModelFactory(
    private val repo: ShoppingRepository
): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(repo) as T
    }
}