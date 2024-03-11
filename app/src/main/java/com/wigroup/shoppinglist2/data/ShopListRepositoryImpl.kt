package com.wigroup.shoppinglist2.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.wigroup.shoppinglist2.domain.ShopItem
import com.wigroup.shoppinglist2.domain.ShopListRepository
import javax.inject.Inject

class ShopListRepositoryImpl @Inject constructor(
    private val shopListDao: ShopListDao,
    private val mapper: ShopListMapper,
) : ShopListRepository {

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopItem.id)
    }

    override suspend fun editShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(mapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun getShopItem(shopItemId: Int): ShopItem {
        val dbModel = shopListDao.getShopItem(shopItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getShopList(): LiveData<List<ShopItem>> =
        MediatorLiveData<List<ShopItem>>().apply {
            addSource(shopListDao.getShopList()) {
                value = mapper.mapListDbModelToListEntity(it)
            }
        }
}