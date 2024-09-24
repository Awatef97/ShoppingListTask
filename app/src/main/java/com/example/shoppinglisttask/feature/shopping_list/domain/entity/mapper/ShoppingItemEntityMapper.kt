package com.example.shoppinglisttask.feature.shopping_list.domain.entity.mapper

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_list.domain.entity.ShoppingItemEntity

fun ShoppingItemEntity.toShoppingItemDto()=
    ShoppingListDto(
        name = name,
        description = description,
        isBought = isBought,
        date = date
    )

fun ShoppingListDto.toShoppingItemEntity()=
    ShoppingItemEntity(
        name = name,
        description = description,
        isBought = isBought,
        date = date
    )