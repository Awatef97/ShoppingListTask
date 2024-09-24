package com.example.shoppinglisttask.feature.shopping_item_creation.di

import com.example.shoppinglisttask.feature.shopping_item_creation.data.repository.ShoppingItemCreationRepositoryImp
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.repository.ShoppingItemCreationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ShoppingItemCreationModule {
    @ViewModelScoped
    @Binds
    internal abstract fun provideShoppingItemsRepository(shoppingItemCreationRepositoryImp: ShoppingItemCreationRepositoryImp): ShoppingItemCreationRepository
}