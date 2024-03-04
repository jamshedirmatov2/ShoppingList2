package com.wigroup.shoppinglist2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.wigroup.shoppinglist2.domain.ShopItem
import com.wigroup.shoppinglist2.domain.ShopListRepository

object ShopListRepositoryImpl : ShopListRepository {
    private val shopListLD = MutableLiveData<List<ShopItem>>()
    private val shopList = mutableListOf<ShopItem>()

    private var autoincrementId = 0

    init {
        for (i in 0 until 10) {
            addShopItem(ShopItem("Name $i", i, true))
        }
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_ID) {
            shopItem.id = autoincrementId++
        }
        shopList.add(shopItem)
        updateList()
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateList()
    }

    override fun editShopItem(shopItem: ShopItem) {
        val oldElement = getShopItem(shopItem.id)
        deleteShopItem(oldElement)
        addShopItem(shopItem)
    }

    override fun getShopItem(shopItemId: Int): ShopItem {
        return shopList.find { it.id == shopItemId }
            ?: throw RuntimeException("ID $shopItemId not found")
    }

    override fun getShopList(): LiveData<List<ShopItem>> = shopListLD

    private fun updateList() {
        shopListLD.value = shopList.toList()
    }
}