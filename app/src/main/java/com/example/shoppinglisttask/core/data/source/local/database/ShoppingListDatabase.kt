package com.example.shoppinglisttask.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.shoppinglisttask.core.data.source.local.database.dao.ShoppingListDao
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto

@Database(
    entities = [ShoppingListDto::class], version = 1, exportSchema = false
)
abstract class ShoppingListDatabase : RoomDatabase(){
    abstract fun shoppingDao(): ShoppingListDao
}
const val SHOPPING_LIST_TABLE = "shopping_list_table"