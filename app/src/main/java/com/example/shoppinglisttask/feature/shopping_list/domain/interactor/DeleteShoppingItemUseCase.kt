package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteShoppingItemUseCase @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
    suspend operator fun invoke(itemId: Int)=
        shoppingListRepository.deleteItem(itemId = itemId)
}