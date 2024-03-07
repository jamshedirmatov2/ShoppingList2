package com.wigroup.shoppinglist2.data

import com.wigroup.shoppinglist2.domain.ShopItem

class ShopListMapper {

    fun mapEntityToDbModel(shopItem: ShopItem) = ShopItemDbModel(
        id = shopItem.id,
        name = shopItem.name,
        count = shopItem.count,
        enabled = shopItem.enabled,
    )

    fun mapDbModelToEntity(shopItemDbModel: ShopItemDbModel) = ShopItem(
        name = shopItemDbModel.name,
        count = shopItemDbModel.count,
        enabled = shopItemDbModel.enabled,
        id = shopItemDbModel.id,
    )

    fun mapListDbModelToListEntity(listDb: List<ShopItemDbModel>) = listDb.map {
        mapDbModelToEntity(it)
    }
}