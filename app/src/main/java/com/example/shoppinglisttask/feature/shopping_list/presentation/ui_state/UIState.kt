package com.example.shoppinglisttask.feature.shopping_list.presentation.ui_state

import com.example.shoppinglisttask.feature.presentation.ui_mode.ShoppingItemUIModel


sealed class UIState {
    data class ShowError(val message: String): UIState()
    data class GetShoppingList(val shoppingListUIModel: List<ShoppingItemUIModel>): UIState()
}