package com.example.shoppinglisttask.feature.shopping_list.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglisttask.feature.core.presentation.ui_model.mapper.toShoppingItemUIModel
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.ChangeBoughtItemUseCase
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.DeleteShoppingItemUseCase
import com.example.shoppinglisttask.feature.shopping_list.domain.interactor.GetShoppingItemsUseCase
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import com.example.shoppinglisttask.feature.shopping_list.presentation.ui_state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.replay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val deleteShoppingItemUseCase: DeleteShoppingItemUseCase,
    private val changeBoughtItemUseCase: ChangeBoughtItemUseCase
): ViewModel(){
    private var _eventFlow = MutableSharedFlow<UIState>(replay = 0)
    val eventFlow = _eventFlow
    fun getShoppingList(getShoppingItemParam: GetShoppingItemParam) {
        viewModelScope.launch {
            try {
                val result = getShoppingItemsUseCase.invoke(getShoppingItemParam)
                    val shoppingListEntity = result.map { it.toShoppingItemUIModel() }
                    _eventFlow.emit(UIState.GetShoppingList(
                        shoppingListUIModel = shoppingListEntity
                    ))


            }catch (e:Exception){
                _eventFlow.emit(UIState.ShowError(message = e.message ?: ""))
            }
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            try {
                deleteShoppingItemUseCase.invoke(itemId)
                _eventFlow.emit(UIState.DeleteItemSuccessfully)
            }catch (e:Exception){
                _eventFlow.emit(UIState.ShowError(message = e.message ?: ""))
            }
        }
    }

    fun updateItem(itemId: Int,isBought: Boolean) {
        viewModelScope.launch {
            try {
                changeBoughtItemUseCase.invoke(itemId = itemId,isBought = isBought)
                _eventFlow.emit(UIState.UpdatedItemSuccessfully)
            }catch (e:Exception){
                _eventFlow.emit(UIState.ShowError(message = e.message ?: ""))
            }
        }
    }
}