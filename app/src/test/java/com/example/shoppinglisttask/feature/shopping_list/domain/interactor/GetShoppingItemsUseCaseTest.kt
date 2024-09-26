package com.example.shoppinglisttask.feature.shopping_list.domain.interactor

import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_list.data.FakeShoppingListRepositoryImp
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetShoppingItemsUseCaseTest {

    lateinit var getShoppingItemsUseCase: GetShoppingItemsUseCase
    private lateinit var fakeShoppingListRepositoryImp: FakeShoppingListRepositoryImp

    @Before
    fun setUp(){
        fakeShoppingListRepositoryImp = FakeShoppingListRepositoryImp()
        getShoppingItemsUseCase = GetShoppingItemsUseCase(fakeShoppingListRepositoryImp)
    }

    @Test
    fun `get all shopping list items`() = runBlocking{
        //given
        val shoppingListDto = mutableListOf<ShoppingListDto>()
        ('a'..'z').forEachIndexed { index, c ->
            shoppingListDto.add(
                ShoppingListDto(
                    id = 1,
                    name = "Test",
                    description = "shopping item description",
                    date = 21232324244,
                    isBought = true,
                    quantity = 3
                )
            )
        }

        val getShoppingItemParam = GetShoppingItemParam(
            isBought = true,
            isAscending = false
        )


        shoppingListDto.forEach { fakeShoppingListRepositoryImp.insert(it) }

        //when
        getShoppingItemsUseCase(getShoppingItemParam)

        // Then
        val items = fakeShoppingListRepositoryImp.getAllItems().first()

        TestCase.assertEquals(items.size,1)
        TestCase.assertEquals(items[0].isBought, false)
    }
}