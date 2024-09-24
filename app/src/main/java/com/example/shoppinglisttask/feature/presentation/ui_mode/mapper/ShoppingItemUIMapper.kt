package com.example.shoppinglisttask.feature.presentation.ui_mode.mapper

import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.presentation.ui_mode.ShoppingItemUIModel


fun ShoppingItemEntity.toShoppingItemUIModel()=
    ShoppingItemUIModel(
        id = id,
        name = name,
        description = description,
        isBought = isBought,
        date = date,
        quantity = quantity
    )

fun ShoppingItemUIModel.toShoppingItemUIModel()=
    ShoppingItemEntity(
        id = id,
        name = name,
        description = description,
        isBought = isBought,
        date = date,
        quantity = quantity
    )