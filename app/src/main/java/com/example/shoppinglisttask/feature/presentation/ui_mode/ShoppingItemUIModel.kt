package com.example.shoppinglisttask.feature.presentation.ui_mode

data class ShoppingItemUIModel(
    val id: Int = 0,
    val name: String,
    val description: String,
    val date: Long,
    val isBought: Boolean,
    val quantity:Int
)