package com.example.shoppinglisttask.feature.shopping_list.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.shoppinglisttask.R
import com.example.shoppinglisttask.databinding.DialogFragmentShoppingItemFilteringBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingItemFilteringDialogFragment: DialogFragment() {
    private  var _binding: DialogFragmentShoppingItemFilteringBinding? = null
    private val binding get() = _binding!!
    private var isBought: Boolean? = null
    private var isAscending: Boolean? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogFragmentShoppingItemFilteringBinding.inflate(inflater, container, false)
        isCancelable = false
        return binding.root
    }
    override fun onResume() {
        super.onResume()
        val width = resources.displayMetrics.widthPixels - 100
        val height = resources.displayMetrics.heightPixels - 300
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_rounded)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListener()
    }

    private fun initListener(){

        updateFilterStates(false)
        updateSortStates(false)
        with(binding){
            btnBought.setOnClickListener {
                updateFilterStates(true)
            }

            btnNotBought.setOnClickListener {
                updateFilterStates(false)
            }
            btnAscending.setOnClickListener {
                updateSortStates(false)
            }
            btnDescending.setOnClickListener {
                updateSortStates(true)
            }
            btnSave.setOnClickListener {
                val result = Bundle().apply {
                    putBoolean("isBought", isBought ?: false)
                    putBoolean("isAscending", isAscending ?: false)
                }
                setFragmentResult("requestKey", result)
                dismiss()
            }

            btnReset.setOnClickListener {
                val result = Bundle().apply {
                    putBoolean("isReset", true)
                }
                setFragmentResult("requestKey", result)
                dismiss()
            }
        }


    }

    private fun updateFilterStates(isBoughtSelected: Boolean) {
        isBought = isBoughtSelected
        if (isBoughtSelected) {
            binding.btnBought.setBackgroundResource(R.drawable.bg_select_filter_field)
            binding.btnNotBought.setBackgroundResource(R.drawable.bg_gray_outline_field)
        } else {
            binding.btnNotBought.setBackgroundResource(R.drawable.bg_select_filter_field)
            binding.btnBought.setBackgroundResource(R.drawable.bg_gray_outline_field)
        }
    }
    private fun updateSortStates(isAscendingOrder: Boolean) {
        isAscending = isAscendingOrder
        if (isAscendingOrder) {
            binding.btnDescending.setBackgroundResource(R.drawable.bg_select_filter_field)
            binding.btnAscending.setBackgroundResource(R.drawable.bg_gray_outline_field)
        } else {
            binding.btnAscending.setBackgroundResource(R.drawable.bg_select_filter_field)
            binding.btnDescending.setBackgroundResource(R.drawable.bg_gray_outline_field)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}