package com.example.shoppinglisttask.feature.shopping_list.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shoppinglisttask.R
import com.example.shoppinglisttask.core.extension.showMessage
import com.example.shoppinglisttask.databinding.FragmentShoppingListBinding
import com.example.shoppinglisttask.feature.core.presentation.ShoppingItemUIModel
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import com.example.shoppinglisttask.feature.shopping_list.presentation.ui_state.UIState
import com.example.shoppinglisttask.feature.shopping_list.presentation.view.adapter.ShoppingListAdapter
import com.example.shoppinglisttask.feature.shopping_list.presentation.viewmodel.ShoppingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListFragment: Fragment() {
    private  var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!
    private val shoppingListViewModel:ShoppingListViewModel by viewModels()

    @Inject
    lateinit var shoppingListAdapter: ShoppingListAdapter
    private var shoppingListUIModel: MutableList<ShoppingItemUIModel> = mutableListOf()
    private lateinit var updatedItem: ShoppingItemUIModel
    private var isAscending = true
    private var isBought = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getShoppingList(
            GetShoppingItemParam(
                isBought = false,
                isAscending = true
            )
        )
        initListener()
        initObservation()
        setFragmentResultListener("requestKey") { key, bundle ->
             isBought = bundle.getBoolean("isBought")
             isAscending = bundle.getBoolean("isAscending")
           if (bundle.getBoolean("isReset") ||bundle.getBoolean("isNewItemAdded")){
                getShoppingList(   GetShoppingItemParam(
                    isBought = false,
                    isAscending = true
                ))
            }else{
                getShoppingList(getShoppingItemParam = GetShoppingItemParam(
                    isBought = isBought,
                    isAscending = isAscending
                ))
            }

        }
    }

    private fun getShoppingList(getShoppingItemParam: GetShoppingItemParam) =
        shoppingListViewModel.getShoppingList(getShoppingItemParam)
    private fun initListener(){
        initRecyclerView()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemCreationDialogFragment(null))
        }
        binding.btnFilter.setOnClickListener {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemFilteringDialogFragment())
        }
        binding.etSearch.addTextChangedListener { text ->
            val query = text.toString()
            searchItems(query)
        }
        shoppingListAdapter.onItemDeleteClicked = {
            shoppingListViewModel.deleteItem(itemId = it)
        }
        shoppingListAdapter.onItemEditClicked = {
            updatedItem = it.copy(isBought = isBought)
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemCreationDialogFragment(it))

        }
        shoppingListAdapter.onItemBoughtClicked = {
            updatedItem = it
            shoppingListViewModel.updateItem(itemId = it.id, isBought = !it.isBought)
        }
    }

    private fun initObservation()= with(shoppingListViewModel){
        lifecycleScope.launchWhenCreated {
                eventFlow.collect { uiState ->
                    when(uiState){
                        is UIState.ShowError -> {uiState.message.showMessage(context = context)}
                        is UIState.GetShoppingList ->{
                            shoppingListUIModel = uiState.shoppingListUIModel as MutableList<ShoppingItemUIModel>
                            shoppingListAdapter.submitList(uiState.shoppingListUIModel)
                            getString(R.string.msg_item_add_successfully).showMessage(context = context)

                        }
                        is UIState.DeleteItemSuccessfully ->{
                           getString(R.string.msg_item_deleted_successfully).showMessage(context = context)
                            getShoppingList(GetShoppingItemParam(isBought = updatedItem.isBought,isAscending = isAscending))
                        }
                        is UIState.UpdatedItemSuccessfully ->{
                            getString(R.string.msg_item_updated_successfully).showMessage(context = context)
                            getShoppingList(GetShoppingItemParam(isBought = updatedItem.isBought,isAscending = isAscending))

                        }
                    }
            }

        }
    }

    private fun initRecyclerView() {
        with(binding.rvShoppingList) {
            adapter = shoppingListAdapter
        }
    }
    private fun searchItems(query: String) {
        val filteredItems = if (query.isEmpty()) {
            shoppingListUIModel
        } else {
            shoppingListUIModel.filter { item ->
                item.name.contains(query, ignoreCase = true) || item.description.contains(query, ignoreCase = true)
            }
        }
        shoppingListAdapter.submitList(filteredItems)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}