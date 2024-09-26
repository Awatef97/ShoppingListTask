package com.example.shoppinglisttask.feature.shopping_item_creation.data.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.repository.ShoppingItemCreationRepository


class FakeShoppingItemCreationRepository:ShoppingItemCreationRepository {

    private val shoppingItemsListDto = mutableListOf<ShoppingListDto>()
    override suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto) {
         shoppingItemsListDto.add(shoppingListDto)
    }

     fun getAllItems(): List<ShoppingListDto> {
        return shoppingItemsListDto
    }
}
