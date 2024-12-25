package com.example.shoppinglist.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.shoppinglist.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var shoppingDatabase:ShoppingDB
    private lateinit var shoppingDao: ShoppingDao

    @Before
    fun setup(){
        shoppingDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingDB::class.java
        )
        .allowMainThreadQueries()
        .build()
        shoppingDao = shoppingDatabase.shoppingDao()
    }

    @Test
    fun insertShoppingItem() = runTest{
        val shoppingItem = ShoppingItem("camera",1,500f,"url", 1)
        shoppingDao.insertShoppingItem(shoppingItem)

        val itemFromDatabase = shoppingDao.getAllItems().getOrAwaitValue()

        assertThat(shoppingItem).isIn(itemFromDatabase)

    }

    @Test
    fun deleteShoppingItem() = runTest {
        val shoppingItem = ShoppingItem("camera",1,500f,"url", 1)
        shoppingDao.insertShoppingItem(shoppingItem)

        shoppingDao.deleteShoppingItem(shoppingItem)

        val itemFromDatabase = shoppingDao.getAllItems().getOrAwaitValue()

        assertThat(shoppingItem).isNotIn(itemFromDatabase)
    }


    @Test
    fun getTotalPrice() = runTest {
        val shoppingItem = ShoppingItem("camera",3,500f,"url", 1)
        val shoppingItem2 = ShoppingItem("mobile",5,100f,"url",2)
        val shoppingItem3 = ShoppingItem("screen",0,100f,"url",3)
        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.insertShoppingItem(shoppingItem2)
        shoppingDao.insertShoppingItem(shoppingItem3)

        val totalPriceFromDatabase = shoppingDao.getTotalPrice().getOrAwaitValue()

        assertThat(totalPriceFromDatabase).isEqualTo(2000f)
    }

    @Test
    fun checkOnConflictStrategy() = runTest {
        val shoppingItem = ShoppingItem("camera",3,500f,"url", 1)
        val shoppingItem2 = ShoppingItem("mobile",5,100f,"url",1)

        shoppingDao.insertShoppingItem(shoppingItem)
        shoppingDao.insertShoppingItem(shoppingItem2)

        val itemFromDatabase = shoppingDao.getAllItems().getOrAwaitValue()

        assertThat(shoppingItem2).isIn(itemFromDatabase)
        assertThat(shoppingItem).isNotIn(itemFromDatabase)
    }

    @After
    fun teardown(){
        shoppingDatabase.close()
    }

}