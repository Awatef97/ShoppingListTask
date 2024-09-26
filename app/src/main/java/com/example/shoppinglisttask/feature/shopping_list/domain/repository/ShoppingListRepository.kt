package com.example.shoppinglisttask.feature.shopping_list.domain.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun deleteItem(itemId: Int)

    suspend fun getAllItems(): List<ShoppingListDto>

    suspend fun updateItem(itemId: Int, isBought: Boolean)

}