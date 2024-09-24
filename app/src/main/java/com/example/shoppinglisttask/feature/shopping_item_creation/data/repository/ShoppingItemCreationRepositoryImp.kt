package com.example.shoppinglisttask.feature.shopping_item_creation.data.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.core.data.source.local.source.ShoppingListLocalDataSource
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.repository.ShoppingItemCreationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingItemCreationRepositoryImp @Inject constructor(
    private val shoppingListLocalDataSource: ShoppingListLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
): ShoppingItemCreationRepository {
    override suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto) {
        withContext(ioDispatcher){
            shoppingListLocalDataSource.insertOrUpdateItem(shoppingListDto)
        }
    }
}