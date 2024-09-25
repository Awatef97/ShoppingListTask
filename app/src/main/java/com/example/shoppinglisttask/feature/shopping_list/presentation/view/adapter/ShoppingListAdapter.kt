package com.example.shoppinglisttask.feature.shopping_list.presentation.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglisttask.databinding.ItemShoppingListBinding
import com.example.shoppinglisttask.feature.core.presentation.ShoppingItemUIModel
import javax.inject.Inject

class ShoppingListAdapter @Inject constructor()
    : ListAdapter<ShoppingItemUIModel,ShoppingListAdapter.ShoppingListViewHolder>(DiffUtilCallback) {
    var onItemDeleteClicked: ((Int) -> Unit) = { _ -> }
    var onItemEditClicked: ((ShoppingItemUIModel) -> Unit) = { _ -> }
    var onItemBoughtClicked: ((ShoppingItemUIModel) -> Unit) = { _ -> }

        inner class ShoppingListViewHolder(private val binding: ItemShoppingListBinding) :
            RecyclerView.ViewHolder(binding.root){
                fun bind(shoppingItemUIModel: ShoppingItemUIModel){
                    with(binding){
                        if (shoppingItemUIModel.isBought)
                            btnBought.visibility = View.GONE
                        tvName.text = shoppingItemUIModel.name
                        tvDescription.text = shoppingItemUIModel.description
                        tvDate.text = shoppingItemUIModel.date.toString()
                        tvQuantity.text = shoppingItemUIModel.quantity.toString()
                        btnDelete.setOnClickListener {  onItemDeleteClicked(shoppingItemUIModel.id)}
                        btnEdit.setOnClickListener {  onItemEditClicked(shoppingItemUIModel)}
                        btnBought.setOnClickListener { onItemBoughtClicked(shoppingItemUIModel) }
                    }
                }

        }
    private object DiffUtilCallback : DiffUtil.ItemCallback<ShoppingItemUIModel>() {
        override fun areItemsTheSame(
            oldItem: ShoppingItemUIModel,
            newItem: ShoppingItemUIModel
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingItemUIModel,
            newItem: ShoppingItemUIModel
        ): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        val binding =
            ItemShoppingListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}