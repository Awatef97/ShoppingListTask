package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_list.data.FakeShoppingListRepositoryImp
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class ChangeBoughtItemUseCaseTest {

    lateinit var changeBoughtItemUseCase: ChangeBoughtItemUseCase
    private lateinit var fakeShoppingListRepositoryImp: FakeShoppingListRepositoryImp

    @Before
    fun setUp(){
        fakeShoppingListRepositoryImp = FakeShoppingListRepositoryImp()
        changeBoughtItemUseCase = ChangeBoughtItemUseCase(fakeShoppingListRepositoryImp)
    }

    @Test
    fun `change bought status for shopping list item`() = runBlocking{
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
        fakeShoppingListRepositoryImp.updateItem(itemId = shoppingListDto.id,isBought = true)

        //when
        changeBoughtItemUseCase(itemId = shoppingListDto.id,isBought = false)

        // Then
        val items = fakeShoppingListRepositoryImp.getAllItems().first()

        assertEquals(items.size,1)
        assertEquals(items[0].isBought, false)
    }

}