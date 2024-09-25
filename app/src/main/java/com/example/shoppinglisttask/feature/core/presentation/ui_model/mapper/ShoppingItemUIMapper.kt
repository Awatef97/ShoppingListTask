package com.example.shoppinglisttask.feature.core.presentation.ui_model.mapper

import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.core.presentation.ShoppingItemUIModel


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