package com.example.shoppinglisttask.feature.shopping_list.domain.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import kotlinx.coroutines.flow.Flow

interface ShoppingListRepository {

    suspend fun deleteItem(shoppingListDto: ShoppingListDto)

    fun getAllItems(): Flow<List<ShoppingListDto>>


}