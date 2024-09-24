package com.example.shoppinglisttask.feature.shopping_item_creation.domain.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto

interface ShoppingItemCreationRepository {
    suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto)
}