package com.example.shoppinglisttask.feature.shopping_list.data.repository

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.core.data.source.local.source.ShoppingListLocalDataSource
import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ShoppingListRepositoryImp @Inject constructor(
    private val shoppingListLocalDataSource: ShoppingListLocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
): ShoppingListRepository {
    override suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto) {
        withContext(ioDispatcher){
            shoppingListLocalDataSource.insertOrUpdateItem(shoppingListDto)
        }
    }

    override suspend fun deleteItem(shoppingListDto: ShoppingListDto) {
        withContext(ioDispatcher){
            shoppingListLocalDataSource.deleteItem(shoppingListDto)
        }
    }

    override fun getAllItems(): Flow<List<ShoppingListDto>> {
        return shoppingListLocalDataSource.getAllItems()
            .flowOn(ioDispatcher)

    }

}