package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import javax.inject.Inject

class DeleteShoppingItem @Inject constructor(
    private val shoppingListRepository: ShoppingListRepository
) {
}