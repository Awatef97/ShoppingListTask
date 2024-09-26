package com.example.shoppinglisttask.core.data.source.local.source

import com.example.shoppinglisttask.core.data.source.local.database.ShoppingListDatabase
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import javax.inject.Inject

class ShoppingListLocalDataSource @Inject constructor(
    private val shoppingListDatabase: ShoppingListDatabase
) {
    suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto)=
        shoppingListDatabase.shoppingDao().insertOrUpdateItem(shoppingListDto)

    suspend fun deleteItem(itemId: Int)=
        shoppingListDatabase.shoppingDao().deleteItemById(itemId)

     suspend fun getAllItems()=
        shoppingListDatabase.shoppingDao().getAllItems()

    suspend fun updateItem(itemId: Int, isBought: Boolean)=
        shoppingListDatabase.shoppingDao().updateBoughtStatus(itemId,isBought)
}