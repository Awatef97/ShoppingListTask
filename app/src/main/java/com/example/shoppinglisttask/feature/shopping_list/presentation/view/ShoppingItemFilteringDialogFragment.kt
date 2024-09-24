package com.example.shoppinglisttask.feature.shopping_list.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.shoppinglisttask.R
import com.example.shoppinglisttask.databinding.DialogFragmentShoppingItemFilteringBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingItemFilteringDialogFragment: DialogFragment() {
    private  var _binding: DialogFragmentShoppingItemFilteringBinding? = null
    private val binding get() = _binding!!


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
        val height = resources.displayMetrics.heightPixels - 200
        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.bg_white_rounded)

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}