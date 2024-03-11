package com.wigroup.shoppinglist2.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wigroup.shoppinglist2.domain.DeleteShopItemUseCase
import com.wigroup.shoppinglist2.domain.EditShopItemUseCase
import com.wigroup.shoppinglist2.domain.GetShopListUseCase
import com.wigroup.shoppinglist2.domain.ShopItem
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    getShopListUseCase: GetShopListUseCase,
    private val deleteShopItemUseCase: DeleteShopItemUseCase,
    private val editShopItemUseCase: EditShopItemUseCase,
) : ViewModel() {

    val shopList = getShopListUseCase.getShopList()

    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        viewModelScope.launch {
            editShopItemUseCase.editShopItem(shopItem.copy(enabled = !shopItem.enabled))
        }
    }
}