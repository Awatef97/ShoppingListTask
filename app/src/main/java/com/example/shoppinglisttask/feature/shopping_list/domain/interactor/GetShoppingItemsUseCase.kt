package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.core.domain.entity.mapper.toShoppingItemEntity
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingItemsUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
     operator fun invoke(getShoppingItemParam: GetShoppingItemParam?): Flow<List<ShoppingItemEntity>> {
         return shoppingListRepository.getAllItems()
             .map { items ->

                 val shoppingItems = items.map { it.toShoppingItemEntity() }

                 val filteredItems = when (getShoppingItemParam?.isBought) {
                     true -> shoppingItems.filter { it.isBought }
                     false -> shoppingItems.filter { !it.isBought }
                     null -> shoppingItems
                 }

                 when (getShoppingItemParam?.isAscending) {
                     true -> filteredItems.sortedBy { it.date }
                     false -> filteredItems.sortedByDescending { it.date }
                     null -> filteredItems
                 }
             }
         }

}