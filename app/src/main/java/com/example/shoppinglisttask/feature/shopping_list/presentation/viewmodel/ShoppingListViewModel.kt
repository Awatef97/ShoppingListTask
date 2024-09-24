package com.example.shoppinglisttask.feature.shopping_list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.presentation.ui_mode.mapper.toShoppingItemUIModel
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.GetShoppingItemsUseCase
import com.example.shoppinglisttask.feature.shopping_list.presentation.ui_state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase
): ViewModel(){
    init {
        getShoppingList()
    }
    private val _eventFlow = MutableSharedFlow<UIState>()
    val eventFlow = _eventFlow.asSharedFlow()
    fun getShoppingList() {
        viewModelScope.launch {
            try {
                getShoppingItemsUseCase.invoke().collectLatest {
                    val shoppingListEntity = it.map { it.toShoppingItemUIModel() }
                    _eventFlow.emit(UIState.GetShoppingList(
                        shoppingListUIModel = shoppingListEntity
                    ))
                }

            }catch (e:Exception){
                _eventFlow.emit(UIState.ShowError(message = e.message ?: ""))
            }
        }
    }
}