package com.example.shoppinglisttask.core.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.formatMillisecondsToDate(): String {
    return try {
        val date = Date(this)
        val format = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
        format.format(date)
    }catch (e:Exception){
        this.toString()
    }

}