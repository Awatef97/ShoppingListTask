package com.example.shoppinglisttask.core.data.source.local.database.dto

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppinglisttask.core.data.source.local.database.SHOPPING_LIST_TABLE

@Entity(tableName = SHOPPING_LIST_TABLE)
data class ShoppingListDto(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val description: String,
    val date: Long,
    val isBought: Boolean
)
