package com.example.shoppinglisttask.core.data.source.local.database.dao

import androidx.room.*
import com.example.shoppinglisttask.core.data.source.local.database.SHOPPING_LIST_TABLE
import com.example.shoppinglisttask.core.data.source.local.database.dto.ShoppingListDto
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrItem(item: ShoppingListDto)

    @Delete
    suspend fun deleteItem(item: ShoppingListDto)

    @Query("SELECT * FROM $SHOPPING_LIST_TABLE")
     fun getAllItems(): Flow<List<ShoppingListDto>>


}