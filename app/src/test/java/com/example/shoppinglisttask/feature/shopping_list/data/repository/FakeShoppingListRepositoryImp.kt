package com.example.shoppinglisttask.feature.shopping_list.data.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository


class FakeShoppingListRepositoryImp: ShoppingListRepository {
    private val shoppingItemsListDto = mutableListOf<ShoppingListDto>()
    override suspend fun deleteItem(itemId: Int) {
        shoppingItemsListDto.remove(shoppingItemsListDto.find { it.id == itemId })
    }

    override suspend fun getAllItems(): List<ShoppingListDto> {
        return shoppingItemsListDto
    }

    override suspend fun updateItem(itemId: Int, isBought: Boolean) {
        shoppingItemsListDto.indexOfFirst { it.id == itemId }.takeIf { it != -1 }?.let { index ->
            val updatedItem = shoppingItemsListDto[index].copy(isBought = isBought)
            shoppingItemsListDto[index] = updatedItem
        }
    }
    fun insert(shoppingListDto: ShoppingListDto) {
        shoppingItemsListDto.add(shoppingListDto)
    }
}