package com.example.shoppinglist.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglist.data.repository.FakeTestShoppingRepo
import com.example.shoppinglist.getOrAwaitValue
import com.example.shoppinglist.util.Constants
import com.example.shoppinglist.util.Status
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest{


    private lateinit var shoppingViewModel: ShoppingViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        shoppingViewModel = ShoppingViewModel(FakeTestShoppingRepo())
    }

    @Test
    fun `insert shopping item with empty field, returns error`(){
        shoppingViewModel.insertShoppingItem("name","","100")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert shopping item with to Long Name, returns error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_NAME_LENGTH + 1)
                append("s")
        }
        shoppingViewModel.insertShoppingItem(string,"1","100")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with to Long price, returns error`(){
        val string = buildString {
            for (i in 1..Constants.MAX_PRICE_LENGTH + 1)
                append(1)
        }
        shoppingViewModel.insertShoppingItem("string","1",string)

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with invalid price, return error`(){
        shoppingViewModel.insertShoppingItem("string","1","string")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with wrong amount, returns error`(){
        shoppingViewModel.insertShoppingItem("name","&^","100")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert shopping item with too high amount, returns error`(){
        shoppingViewModel.insertShoppingItem("name","99999999999999999999999999","100")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert valid item, returns success`(){
        shoppingViewModel.insertShoppingItem("name","5","3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)
    }

    @Test
    fun `current url is empty after inserting, return true`(){
        shoppingViewModel.insertShoppingItem("name","5","3.0")

        val value = shoppingViewModel.insertShoppingItemStatus.getOrAwaitValue()

        assertThat(shoppingViewModel.curImageUrl.value).isEqualTo("")
    }

    @Test
    fun `current url changing when using setCurUrl, return true`(){
        val imageUrl = "google.com"
        shoppingViewModel.setCurImageUrl(imageUrl)

        val value = shoppingViewModel.curImageUrl.value

        assertThat(value).isEqualTo(imageUrl)
    }

    @After
    fun tear(){

    }
}