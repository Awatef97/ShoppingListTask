package com.example.shoppinglisttask.feature.shopping_item_creation.domain.interactor

import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.core.domain.entity.mapper.toShoppingItemDto
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.repository.ShoppingItemCreationRepository
import javax.inject.Inject

class AddShoppingItemUseCase @Inject constructor(
    private val shoppingItemCreationRepository: ShoppingItemCreationRepository
) {
    suspend operator fun invoke(shoppingItemEntity: ShoppingItemEntity)=
        shoppingItemCreationRepository.insertOrUpdateItem(shoppingItemEntity.toShoppingItemDto())
}