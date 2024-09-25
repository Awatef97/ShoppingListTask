package com.example.shoppinglisttask.feature.shopping_item_creation.data.repository

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.repository.ShoppingItemCreationRepository
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class FakeShoppingItemCreationRepository:ShoppingItemCreationRepository {

    private val shoppingItemsListDto = mutableListOf<ShoppingListDto>()
    override suspend fun insertOrUpdateItem(shoppingListDto: ShoppingListDto) {
         shoppingItemsListDto.add(shoppingListDto)
    }

     fun getAllItems(): List<ShoppingListDto> {
        return shoppingItemsListDto
    }
}
