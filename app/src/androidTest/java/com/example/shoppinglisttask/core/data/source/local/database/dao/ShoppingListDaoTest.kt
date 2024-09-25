package com.example.shoppinglisttask.core.data.source.local.database.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.runner.AndroidJUnit4
import com.example.shoppinglisttask.core.data.source.local.database.ShoppingListDatabase
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ShoppingListDaoTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("shopping_list_table")
    private lateinit var db: ShoppingListDatabase
    private lateinit var shoppingListDao: ShoppingListDao


@Before
fun initDb() {
    db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        ShoppingListDatabase::class.java
    ).allowMainThreadQueries().build()
    shoppingListDao = db.shoppingDao()
}
    @Test
    fun insert_shopping_list_item_return_item(): Unit = runBlocking {
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
        shoppingListDao.insertOrUpdateItem(item = shoppingListDto)

        // Then
        val shoppingList: List<ShoppingListDto> = shoppingListDao.getAllItems().first()

        assertEquals(1, shoppingList.size)
        assertEquals(shoppingListDto, shoppingList[0])
    }

    @Test
    fun remove_shopping_item_return_empty(): Unit = runBlocking {
        // Given
        val shoppingListDto = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )
        shoppingListDao.insertOrUpdateItem(shoppingListDto)
        // When
        shoppingListDao.deleteItemById(itemId = 1)

        // Then
        val shoppingList: List<ShoppingListDto> = shoppingListDao.getAllItems().first()

        assertEquals(0, shoppingList.size)
        assertTrue(shoppingList.isEmpty())
    }

    @Test
    fun get_shopping_list_item_return_all_inserted_items_size(): Unit = runBlocking {
        // Given
        val shoppingItemDto1 = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )

        val shoppingItemDto2 = ShoppingListDto(
            id = 2,
            name = "Test2",
            description = "shopping item description2",
            date = 2123232424455,
            isBought = true,
            quantity = 4
        )

        // When
        shoppingListDao.insertOrUpdateItem(item = shoppingItemDto1)
        shoppingListDao.insertOrUpdateItem(item = shoppingItemDto2)

        // Then
        val shoppingList: List<ShoppingListDto> = shoppingListDao.getAllItems().first()

        assertEquals(2, shoppingList.size)
    }

    @Test
    fun update_bought_status_return_shopping_item_with_bought_status_true(): Unit = runBlocking {
        // Given
        val shoppingItemDto1 = ShoppingListDto(
            id = 1,
            name = "Test",
            description = "shopping item description",
            date = 21232324244,
            isBought = true,
            quantity = 3
        )


        // When
        shoppingListDao.insertOrUpdateItem(item = shoppingItemDto1)

        // Then
        val shoppingList: List<ShoppingListDto> = shoppingListDao.getAllItems().first()

        assertEquals(true, shoppingList[0].isBought)
    }
    @After
    fun tearDown() {
        db.close()
    }


}