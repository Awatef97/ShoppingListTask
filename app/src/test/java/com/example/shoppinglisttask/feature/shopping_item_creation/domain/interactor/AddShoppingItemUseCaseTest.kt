package com.example.shoppinglisttask.feature.shopping_item_creation.domain.interactor

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.core.domain.entity.mapper.toShoppingItemEntity
import com.example.shoppinglisttask.feature.shopping_item_creation.data.repository.FakeShoppingItemCreationRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class AddShoppingItemUseCaseTest {
    private lateinit var addShoppingItemUseCase: AddShoppingItemUseCase
    private lateinit var fakeShoppingItemCreationRepository: FakeShoppingItemCreationRepository

    @Before
    fun setUp() {
        fakeShoppingItemCreationRepository = FakeShoppingItemCreationRepository()
        addShoppingItemUseCase = AddShoppingItemUseCase(fakeShoppingItemCreationRepository)

    }

    @Test
    fun `insert shopping list item`(){
        //given
        val shoppingListDto = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )
        runBlocking {
            fakeShoppingItemCreationRepository.insertOrUpdateItem(shoppingListDto)
        }

        //when
        runBlocking{
            addShoppingItemUseCase.invoke(shoppingListDto.toShoppingItemEntity())
        }
        // Then
        val items = fakeShoppingItemCreationRepository.getAllItems()

        val addedItem = items.first()
        assertEquals(shoppingListDto.name, addedItem.name)
        assertEquals(shoppingListDto.description, addedItem.description)
        assertEquals(shoppingListDto.isBought, addedItem.isBought)
        assertEquals(shoppingListDto.quantity, addedItem.quantity)

    }
}