package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.feature.core.domain.entity.mapper.toShoppingItemEntity
import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetShoppingItemsUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
     operator fun invoke() = shoppingListRepository.getAllItems().map {
         it.map {
             it.toShoppingItemEntity()
         }
     }
}