package com.example.shoppinglisttask.feature.core.presentation

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShoppingItemUIModel(
    val id: Int = 0,
    val name: String,
    val description: String,
    val date: Long,
    val isBought: Boolean,
    val quantity:Int
): Parcelable