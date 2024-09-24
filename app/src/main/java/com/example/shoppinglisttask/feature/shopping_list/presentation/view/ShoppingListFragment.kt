package com.example.shoppinglisttask.feature.shopping_list.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.shoppinglisttask.core.extension.showMessage
import com.example.shoppinglisttask.databinding.FragmentShoppingListBinding
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
    }

    private fun getShoppingList() = shoppingListViewModel.getShoppingList()
    private fun initListener(){
        initRecyclerView()
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        binding.fab.setOnClickListener {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemCreationDialogFragment())
        }
        binding.iconFilter.setOnClickListener {
            findNavController().navigate(ShoppingListFragmentDirections.actionShoppingListFragmentToShoppingItemFilteringDialogFragment())
        }
    }

    private fun initObservation()= with(shoppingListViewModel){
        lifecycleScope.launchWhenStarted {
            eventFlow.collectLatest { uiState ->
                when(uiState){
                    is UIState.ShowError -> {uiState.message.showMessage(context = context)}
                    is UIState.GetShoppingList ->{
                        shoppingListAdapter.submitList(uiState.shoppingListUIModel)
                        "Item Added Successfully ${uiState.shoppingListUIModel.size}".showMessage(context = context)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}