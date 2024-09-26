package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_list.data.FakeShoppingListRepositoryImp
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class DeleteShoppingItemUseCaseTest {

    private lateinit var deleteShoppingItemUseCase: DeleteShoppingItemUseCase
    private lateinit var fakeShoppingListRepositoryImp: FakeShoppingListRepositoryImp


    @Before
    fun setUp(){
        fakeShoppingListRepositoryImp = FakeShoppingListRepositoryImp()
        deleteShoppingItemUseCase = DeleteShoppingItemUseCase(fakeShoppingListRepositoryImp)
    }

    @Test
    fun `delete shopping list item`() = runBlocking{
        //given
        val shoppingListDto = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )

        fakeShoppingListRepositoryImp.insert(shoppingListDto)
        fakeShoppingListRepositoryImp.insert(shoppingListDto.copy(id = 3))
        fakeShoppingListRepositoryImp.deleteItem(shoppingListDto.id)

        //when
        deleteShoppingItemUseCase(shoppingListDto.id)

        // Then
        val items = fakeShoppingListRepositoryImp.getAllItems().first()

        assertEquals(items.size, 1)
    }
}