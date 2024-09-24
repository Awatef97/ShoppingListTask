package com.example.shoppinglisttask.core.data.source.local.database.dao

import androidx.room.*
import com.example.shoppinglisttask.core.data.source.local.database.SHOPPING_LIST_TABLE
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateItem(item: ShoppingListDto)

    @Query("DELETE FROM $SHOPPING_LIST_TABLE WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)

    @Query("SELECT * FROM $SHOPPING_LIST_TABLE")
     fun getAllItems(): Flow<List<ShoppingListDto>>

    @Query("UPDATE $SHOPPING_LIST_TABLE SET isBought = :isBought WHERE id = :itemId")
    suspend fun updateBoughtStatus(itemId: Int, isBought: Boolean)

}