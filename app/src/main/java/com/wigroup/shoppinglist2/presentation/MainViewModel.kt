package com.wigroup.shoppinglist2.presentation

import androidx.lifecycle.ViewModel
import com.wigroup.shoppinglist2.data.ShopListRepositoryImpl
import com.wigroup.shoppinglist2.domain.DeleteShopItemUseCase
import com.wigroup.shoppinglist2.domain.EditShopItemUseCase
import com.wigroup.shoppinglist2.domain.GetShopListUseCase
import com.wigroup.shoppinglist2.domain.ShopItem

class MainViewModel : ViewModel() {
    private val repository = ShopListRepositoryImpl

    private val getShopListUseCase = GetShopListUseCase(repository)
    private val deleteShopItemUseCase = DeleteShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        editShopItemUseCase.editShopItem(shopItem.copy(enabled = !shopItem.enabled))
    }
}