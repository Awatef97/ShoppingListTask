package com.example.shoppinglisttask.feature.shopping_list.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.ChangeBoughtItemUseCase
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.DeleteShoppingItemUseCase
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.GetShoppingItemsUseCase
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import com.example.shoppinglisttask.feature.shopping_list.presentation.ui_state.UIState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ShoppingListViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ShoppingListViewModel
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase = mockk(relaxed = true)
    private val changeBoughtItemUseCase: ChangeBoughtItemUseCase = mockk(relaxed = true)
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase = mockk(relaxed = true)
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        viewModel = ShoppingListViewModel(
            changeBoughtItemUseCase = changeBoughtItemUseCase,
            deleteShoppingItemUseCase = deleteShoppingItemUseCase,
            getShoppingItemsUseCase = getShoppingItemsUseCase
        )
        testScope = TestScope(testDispatcher)
    }

        @Test
        fun `get shopping Item emits Shopping List Items state on success`() = testScope.runTest {
            // Given
            val getShoppingItemParam = GetShoppingItemParam(
                isBought = true,
                isAscending = false
            )
            // When
            launch {
                viewModel.getShoppingList(getShoppingItemParam)
            }

            viewModel.eventFlow.take(1).collect { state ->
                coVerify { getShoppingItemsUseCase.invoke(getShoppingItemParam) }
                TestCase.assertTrue(state is UIState.GetShoppingList)
            }
        }
        @Test
        fun `get shopping Item emits ShowError state on failure`() = testScope.runTest {
            // Given
            val getShoppingItemParam = GetShoppingItemParam(
                isBought = true,
                isAscending = false
            )
            val errorMessage = "An error occurred"

            coEvery { getShoppingItemsUseCase.invoke(getShoppingItemParam) } throws Exception(errorMessage)

            // When
            viewModel.getShoppingList(getShoppingItemParam)

            viewModel.eventFlow.take(1).collect { state ->
                TestCase.assertTrue(state is UIState.ShowError)
                TestCase.assertEquals((state as UIState.ShowError).message, errorMessage)

        }
    }

    @Test
    fun `change bought status emits shopping list item with new status state on success`() = testScope.runTest {
        // Given
        val shoppingListDto = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )
        // When
        launch {
            viewModel.updateItem(itemId = shoppingListDto.id,isBought = shoppingListDto.isBought)
        }

        viewModel.eventFlow.take(1).collect { state ->
            coVerify { changeBoughtItemUseCase.invoke(itemId = shoppingListDto.id,isBought = shoppingListDto.isBought) }
            TestCase.assertTrue(state is UIState.UpdatedItemSuccessfully)
        }
    }
    @Test
    fun `change shopping Item emits ShowError state on failure`() = testScope.runTest {
        // Given
        val shoppingListDto = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )
        val errorMessage = "An error occurred"

        coEvery { changeBoughtItemUseCase.invoke(itemId = shoppingListDto.id,isBought = shoppingListDto.isBought) } throws Exception(errorMessage)

        // When
        viewModel.updateItem(itemId = shoppingListDto.id,isBought = shoppingListDto.isBought)

        viewModel.eventFlow.take(1).collect { state ->
            TestCase.assertTrue(state is UIState.ShowError)
            TestCase.assertEquals((state as UIState.ShowError).message, errorMessage)

        }
    }

    @Test
    fun `delete shopping Item emits Delete successful state on success`() = testScope.runTest {
        // Given
        val id = 1
        // When
        launch {
            viewModel.deleteItem(id)
        }

        viewModel.eventFlow.take(1).collect { state ->
            coVerify { deleteShoppingItemUseCase(1) }
            TestCase.assertTrue(state is UIState.DeleteItemSuccessfully)
        }
    }
    @Test
    fun `delete shopping Item emits ShowError state on failure`() = testScope.runTest {
        // Given
        val id = 1
        val errorMessage = "An error occurred"

        coEvery { deleteShoppingItemUseCase(id) } throws Exception(errorMessage)

        // When
        viewModel.deleteItem(1)

        viewModel.eventFlow.take(1).collect { state ->
            TestCase.assertTrue(state is UIState.ShowError)
            TestCase.assertEquals((state as UIState.ShowError).message, errorMessage)

        }
    }
}