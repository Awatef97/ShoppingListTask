package com.example.shoppinglisttask.core.extension

import android.content.Context
import android.widget.Toast

fun String.showMessage(context:Context?) = Toast.makeText(context,this, Toast.LENGTH_SHORT).show()
