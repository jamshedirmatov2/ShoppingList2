package com.wigroup.shoppinglist2.domain

import javax.inject.Inject

class GetShopItemUseCase @Inject constructor(private val shopListRepository: ShopListRepository) {
    suspend fun getShopItem(shopItemId: Int): ShopItem = shopListRepository.getShopItem(shopItemId)
}