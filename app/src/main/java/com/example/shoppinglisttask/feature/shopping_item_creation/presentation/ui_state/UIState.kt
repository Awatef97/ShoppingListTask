package com.example.shoppinglisttask.feature.shopping_item_creation.presentation.ui_state


sealed class UIState {
    data class ShowError(val message: String): UIState()
    object AddShoppingItem: UIState()
}