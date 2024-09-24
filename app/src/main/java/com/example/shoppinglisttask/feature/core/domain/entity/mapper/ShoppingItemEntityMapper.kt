package com.example.shoppinglisttask.feature.core.domain.entity.mapper

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity

fun ShoppingItemEntity.toShoppingItemDto()=
    ShoppingListDto(
        id = id,
        name = name,
        description = description,
        isBought = isBought,
        date = date,
        quantity = quantity
    )

fun ShoppingListDto.toShoppingItemEntity()=
    ShoppingItemEntity(
        id = id,
        name = name,
        description = description,
        isBought = isBought,
        date = date,
        quantity = quantity
    )