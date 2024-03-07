package com.wigroup.shoppinglist2.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.wigroup.shoppinglist2.R
import com.wigroup.shoppinglist2.databinding.ItemShopDisabledBinding
import com.wigroup.shoppinglist2.databinding.ItemShopEnabledBinding
import com.wigroup.shoppinglist2.domain.ShopItem

class ShopListAdapter :
    ListAdapter<ShopItem, ShopItemViewHolder>(ShopItemDiffCallback()) {

    private var onLongClickListener: (ShopItem) -> Unit = {}
    private var onClickListener: (ShopItem) -> Unit = {}

    fun setOnLongClickListener(onLongClickListener: (ShopItem) -> Unit) {
        this.onLongClickListener = onLongClickListener
    }

    fun setOnClickListener(onClickListener: (ShopItem) -> Unit) {
        this.onClickListener = onClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
        val resource = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown view type: $viewType")
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            resource,
            parent,
            false
        )
        return ShopItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = getItem(position)
        val binding = holder.binding
        binding.root.setOnLongClickListener {
            onLongClickListener(shopItem)
            true
        }
        binding.root.setOnClickListener { onClickListener(shopItem) }
        when (binding) {
            is ItemShopEnabledBinding -> binding.shopItem = shopItem
            is ItemShopDisabledBinding -> binding.shopItem = shopItem
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return if (item.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1

        const val MAX_POOL_SIZE = 20
    }
}