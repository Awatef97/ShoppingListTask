package com.example.shoppinglisttask.feature.shopping_list.domain.entity


data class ShoppingItemEntity(
    val name: String,
    val description: String,
    val date: Long,
    val isBought: Boolean
)
