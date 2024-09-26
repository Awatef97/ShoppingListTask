package com.example.shoppinglisttask.feature.shopping_item_creation.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.shoppinglisttask.R
import com.example.shoppinglisttask.core.extension.showMessage
import com.example.shoppinglisttask.databinding.DialogFragmentShoppingItemCreationBinding
import com.example.shoppinglisttask.feature.core.domain.entity.ShoppingItemEntity
import com.example.shoppinglisttask.feature.core.presentation.ShoppingItemUIModel
import com.example.shoppinglisttask.feature.shopping_item_creation.presentation.ui_state.UIState
import com.example.shoppinglisttask.feature.shopping_item_creation.presentation.viewmodel.ShoppingItemCreationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class ShoppingItemCreationDialogFragment: DialogFragment() {
    private  var _binding: DialogFragmentShoppingItemCreationBinding? = null
    private val binding get() = _binding!!
    private val shoppingItemCreationViewModel: ShoppingItemCreationViewModel by viewModels()
    private val args: ShoppingItemCreationDialogFragmentArgs by navArgs()
    private var shoppingItem: ShoppingItemUIModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentShoppingItemCreationBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels - 100
        val height = resources.displayMetrics.heightPixels - 200
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_rounded)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListener()
        initObservation()
    }
    private fun initViews(){
         shoppingItem = args.shoppingItemUIModel
        if (shoppingItem != null){
            with(binding){
                btnAddItem.text = getText(R.string.lbl_update_item)
                etName.setText(shoppingItem?.name)
                etDescription.setText(shoppingItem?.description)
                etQuantity.setText(shoppingItem?.quantity.toString())
            }
        }
    }

    private fun initListener(){

        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnAddItem.setOnClickListener {
            val name = binding.etName.text.toString()
            val description = binding.etDescription.text.toString()
            val quantity = binding.etQuantity.text.toString()
            if (name.isBlank() || name.isEmpty() ||description.isBlank() || description.isEmpty() ||
                quantity.isBlank() || quantity.isEmpty() || quantity == "0" ){
                getString(R.string.msg_validation_error).showMessage(context = context)
            }else{
                val shoppingItemEntity =  ShoppingItemEntity(
                    id = shoppingItem?.id ?: 0,
                    name = name,
                    description = description,
                    quantity = quantity.toInt(),
                    date = System.currentTimeMillis(),
                    isBought = false
                )
                shoppingItemCreationViewModel.addShoppingItem(shoppingItemEntity)
            }

        }
    }
    private fun initObservation()= with(shoppingItemCreationViewModel){
        lifecycleScope.launchWhenStarted {
            eventFlow.collectLatest { uiState ->
                when(uiState){
                    is UIState.ShowError -> {uiState.message.showMessage(context = context)}
                    is UIState.AddShoppingItem ->{
                        getString(R.string.msg_item_add_successfully).showMessage(context = context)
                        val result = Bundle().apply {
                            putBoolean("isNewItemAdded", true)
                        }
                        setFragmentResult("requestKey", result)
                        dismiss()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}