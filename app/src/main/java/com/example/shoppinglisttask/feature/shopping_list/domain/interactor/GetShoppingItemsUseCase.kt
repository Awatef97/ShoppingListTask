package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.core.domain.entity.mapper.toShoppingItemEntity
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import javax.inject.Inject

class GetShoppingItemsUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
     suspend operator fun invoke(getShoppingItemParam: GetShoppingItemParam): List<ShoppingItemEntity> {
                 val shoppingItems = shoppingListRepository.getAllItems().map { it.toShoppingItemEntity() }

                 val filteredItems = when (getShoppingItemParam.isBought) {
                     true -> shoppingItems.filter { it.isBought }
                     false -> shoppingItems.filter { !it.isBought }
                 }

                 return when (getShoppingItemParam.isAscending) {
                     true -> filteredItems.sortedBy { it.date }
                     false -> filteredItems.sortedByDescending { it.date }
                 }

         }

}