package com.example.shoppinglisttask.core.data.source.local.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglisttask.core.data.source.local.database.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    internal fun provideRoomDB(@ApplicationContext applicationContext: Context): ShoppingListDatabase {
        val tempInstance = INSTANCE
        if (tempInstance != null) {
            return tempInstance
        }
        synchronized(this) {
            val instance = Room.databaseBuilder(
                applicationContext,
                ShoppingListDatabase::class.java, "shopping-list-db"
            )
                .fallbackToDestructiveMigration()
                .setQueryCallback(
                    object : RoomDatabase.QueryCallback {
                        override fun onQuery(sqlQuery: String, bindArgs: List<Any?>) {
                            println("SQL Query: $sqlQuery SQL Args: $bindArgs")
                        }
                    },
                    Executors.newSingleThreadExecutor()
                )
                .build()
            INSTANCE = instance
            return instance
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ShoppingListDatabase? = null
    }
}