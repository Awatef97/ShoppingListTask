package com.example.shoppinglisttask.feature.core.domain.entity


data class ShoppingItemEntity(
    val id: Int = 0,
    val name: String,
    val description: String,
    val date: Long,
    val isBought: Boolean,
    val quantity:Int
)
