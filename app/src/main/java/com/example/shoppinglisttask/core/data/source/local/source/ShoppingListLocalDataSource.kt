package com.example.shoppinglisttask.core.data.source.local.source

import com.example.shoppinglisttask.core.data.source.local.database.ShoppingListDatabase
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import javax.inject.Inject

class ShoppingListLocalDataSource @Inject constructor(
    private val shoppingListDatabase: ShoppingListDatabase
) {
    suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto)=
        shoppingListDatabase.shoppingDao().insertOrItem(shoppingListDto)

    suspend fun deleteItem(shoppingListDto: ShoppingListDto)=
        shoppingListDatabase.shoppingDao().deleteItem(shoppingListDto)

     fun getAllItems()=
        shoppingListDatabase.shoppingDao().getAllItems()
}