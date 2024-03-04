package com.wigroup.shoppinglist2.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wigroup.shoppinglist2.R
import com.wigroup.shoppinglist2.domain.ShopItem

class ShopListAdapter : Adapter<ShopListAdapter.ShopItemViewHolder>() {
    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

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
        return ShopItemViewHolder(
            LayoutInflater.from(parent.context).inflate(resource, parent, false)
        )
    }

    override fun getItemCount(): Int = shopList.size

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem = shopList[position]
        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
        holder.itemView.setOnLongClickListener {
            onLongClickListener(shopItem)
            true
        }
        holder.itemView.setOnClickListener { onClickListener(shopItem) }
    }

    override fun getItemViewType(position: Int): Int {
        val item = shopList[position]
        return if (item.enabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    class ShopItemViewHolder(itemView: View) : ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvCount: TextView = itemView.findViewById(R.id.tv_count)
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 0
        const val VIEW_TYPE_DISABLED = 1

        const val MAX_POOL_SIZE = 20
    }
}