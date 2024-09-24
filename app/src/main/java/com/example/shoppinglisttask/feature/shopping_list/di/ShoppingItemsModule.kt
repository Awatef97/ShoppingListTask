package com.example.shoppinglisttask.feature.shopping_list.di

import com.example.shoppinglisttask.feature.shopping_list.data.repository.ShoppingListRepositoryImp
import com.example.shoppinglisttask.feature.shopping_list.domain.repository.ShoppingListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ShoppingItemsModule {
    @ViewModelScoped
    @Binds
    internal abstract fun provideShoppingItemsRepository(shoppingListRepositoryImp: ShoppingListRepositoryImp): ShoppingListRepository
}