package com.wigroup.shoppinglist2.domain

class GetShopListUseCase(private val shopListRepository: ShopListRepository) {
    fun getShopList(): List<ShopItem> = shopListRepository.getShopList()
}