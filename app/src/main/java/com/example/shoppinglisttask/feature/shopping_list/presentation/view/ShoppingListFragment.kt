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
import com.example.shoppinglisttask.core.extension.showMessage
import com.example.shoppinglisttask.databinding.FragmentShoppingListBinding
import com.example.shoppinglisttask.feature.core.presentation.ShoppingItemUIModel
import com.example.shoppinglisttask.feature.shopping_list.domain.param.GetShoppingItemParam
import com.example.shoppinglisttask.feature.shopping_list.presentation.ui_state.UIState
import com.example.shoppinglisttask.feature.shopping_list.presentation.view.adapter.ShoppingListAdapter
import com.example.shoppinglisttask.feature.shopping_list.presentation.viewmodel.ShoppingListViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingListFragment: Fragment() {
    private  var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!
    private val shoppingListViewModel:ShoppingListViewModel by viewModels()

    @Inject
    lateinit var shoppingListAdapter: ShoppingListAdapter
    private var shoppingListUIModel: List<ShoppingItemUIModel> = emptyList()

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
        getShoppingList()
        initListener()
        initObservation()
        setFragmentResultListener("requestKey") { key, bundle ->
            val isBought = bundle.getBoolean("isBought")
            val isAscending = bundle.getBoolean("isAscending")
            if (bundle.getBoolean("isReset")){
                getShoppingList()
            }else{
                getShoppingList(getShoppingItemParam = GetShoppingItemParam(
                    isBought = isBought,
                    isAscending = isAscending
                ))
            }

        }
    }

    private fun getShoppingList(getShoppingItemParam: GetShoppingItemParam? = null) =
        shoppingListViewModel.getShoppingList(getShoppingItemParam)
    private fun initListener(){
        initRecyclerView()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemCreationDialogFragment(null))
        }
        binding.iconFilter.setOnClickListener {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemFilteringDialogFragment())
        }
        binding.etSearch.addTextChangedListener { text ->
            val query = text.toString()
            filterItems(query)
        }
        shoppingListAdapter.onItemDeleteClicked = {
            shoppingListViewModel.deleteItem(itemId = it)
        }
        shoppingListAdapter.onItemEditClicked = {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemCreationDialogFragment(it))

        }
        shoppingListAdapter.onItemBoughtClicked = {
            shoppingListViewModel.updateItem(itemId = it.id, isBought = true)
        }
    }

    private fun initObservation()= with(shoppingListViewModel){
        lifecycleScope.launchWhenStarted {
            eventFlow.collectLatest { uiState ->
                when(uiState){
                    is UIState.ShowError -> {uiState.message.showMessage(context = context)}
                    is UIState.GetShoppingList ->{
                        shoppingListUIModel = uiState.shoppingListUIModel
                        shoppingListAdapter.submitList(uiState.shoppingListUIModel)
                        "Item Added Successfully ${uiState.shoppingListUIModel.size}".showMessage(context = context)

                    }
                    is UIState.DeleteItemSuccessfully ->{
                        "Item Deleted Successfully".showMessage(context = context)
                    }
                    is UIState.UpdatedItemSuccessfully ->{

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
    private fun filterItems(query: String) {
        val filteredItems = if (query.isEmpty()) {
            shoppingListUIModel
        } else {
            shoppingListUIModel.filter { item ->
                item.name.contains(query, ignoreCase = true)
            }
        }
        shoppingListAdapter.submitList(filteredItems)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}