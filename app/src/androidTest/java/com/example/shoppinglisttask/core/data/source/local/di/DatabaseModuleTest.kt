package com.example.shoppinglisttask.core.data.source.local.di

import android.app.Application
import androidx.room.Room
import com.example.shoppinglisttask.core.data.source.local.database.ShoppingListDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModuleTest {

    @Provides
    @Singleton
    @Named("shopping_list_table")
    fun provideNoteDatabase(app: Application): ShoppingListDatabase {
        return Room.inMemoryDatabaseBuilder(
            app,
            ShoppingListDatabase::class.java,
        ).build()
    }
}