package com.example.shoppinglist.data.local

import androidx.room.Database


@Database(
    entities = [ShoppingItem::class]
    ,version = 1
)
abstract class ShoppingDB {

    abstract fun shoppingDao():ShoppingDao
}