import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.interactor.AddShoppingItemUseCase
import com.example.shoppinglisttask.feature.shopping_item_creation.presentation.ui_state.UIState
import com.example.shoppinglisttask.feature.shopping_item_creation.presentation.viewmodel.ShoppingItemCreationViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
class ShoppingItemCreationViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ShoppingItemCreationViewModel
    private val addShoppingItemUseCase: AddShoppingItemUseCase = mockk(relaxed = true)
    private lateinit var testDispatcher: TestDispatcher
    private lateinit var testScope: TestScope

    @Before
    fun setup() {
        testDispatcher = StandardTestDispatcher()
        Dispatchers.setMain(testDispatcher)

        viewModel = ShoppingItemCreationViewModel(addShoppingItemUseCase)
        testScope = TestScope(testDispatcher)
    }

    @Test
    fun `addShoppingItem emits AddShoppingItem state on success`() = testScope.runTest {
        // Given
        val shoppingItemEntity = ShoppingItemEntity(
            id = 1,
            name = "Test Item",
            description = "Test Description",
            quantity = 1,
            isBought = false,
            date = 2568
        )

        // When
        launch {
            viewModel.addShoppingItem(shoppingItemEntity)
        }

        viewModel.eventFlow.take(1).collect { state ->
            assertTrue(state is UIState.AddShoppingItem)
            coVerify { addShoppingItemUseCase.invoke(shoppingItemEntity) }
        }
    }
    @Test
    fun `addShoppingItem emits ShowError state on failure`() = testScope.runTest {
        // Given
        val shoppingItemEntity = ShoppingItemEntity(
            id = 1,
            name = "Test Item",
            description = "Test Description",
            quantity = 1,
            isBought = false,
            date = 2568
        )
        val errorMessage = "An error occurred"

        coEvery { addShoppingItemUseCase.invoke(shoppingItemEntity) } throws Exception(errorMessage)

        // When
        viewModel.addShoppingItem(shoppingItemEntity)

        viewModel.eventFlow.take(1).collect { state ->
            assertTrue(state is UIState.ShowError)
            assertEquals((state as UIState.ShowError).message, errorMessage)
        }

        testDispatcher.scheduler.advanceUntilIdle()
    }
}
