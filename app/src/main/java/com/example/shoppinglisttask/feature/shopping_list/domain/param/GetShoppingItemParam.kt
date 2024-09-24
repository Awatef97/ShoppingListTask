package com.example.shoppinglisttask.feature.shopping_list.domain.param

data class GetShoppingItemParam(
    val isBought: Boolean? = null,
    val isAscending: Boolean? = null
)