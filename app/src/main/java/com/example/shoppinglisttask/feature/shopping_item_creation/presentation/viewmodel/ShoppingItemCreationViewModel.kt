package com.example.shoppinglisttask.feature.shopping_item_creation.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.shopping_item_creation.presentation.ui_state.UIState
import com.example.shoppinglisttask.feature.shopping_item_creation.domain.interactor.AddShoppingItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingItemCreationViewModel @Inject constructor(
    private val addShoppingItemUseCase: AddShoppingItemUseCase
): ViewModel(){
    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()
    fun addShoppingItem(shoppingItemEntity: ShoppingItemEntity) {
        viewModelScope.launch {
            try {
                addShoppingItemUseCase.invoke(shoppingItemEntity = shoppingItemEntity)
                _eventFlow.emit(UIState.AddShoppingItem)
            }catch (e:Exception){
                _eventFlow.emit(UIState.ShowError(message = e.message ?: ""))
            }
        }
    }
}