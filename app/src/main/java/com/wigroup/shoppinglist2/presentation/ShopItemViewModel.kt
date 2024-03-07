package com.wigroup.shoppinglist2.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wigroup.shoppinglist2.data.ShopListRepositoryImpl
import com.wigroup.shoppinglist2.domain.AddShopItemUseCase
import com.wigroup.shoppinglist2.domain.EditShopItemUseCase
import com.wigroup.shoppinglist2.domain.GetShopItemUseCase
import com.wigroup.shoppinglist2.domain.ShopItem
import kotlinx.coroutines.launch

class ShopItemViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ShopListRepositoryImpl(application)

    private val getShopItemUseCase = GetShopItemUseCase(repository)
    private val addShopItemUseCase = AddShopItemUseCase(repository)
    private val editShopItemUseCase = EditShopItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItemLD = MutableLiveData<ShopItem>()
    val shopItemLD: LiveData<ShopItem>
        get() = _shopItemLD

    private val _closeScreenLD = MutableLiveData<Unit>()
    val closeScreenLD: LiveData<Unit>
        get() = _closeScreenLD

    fun getShopItem(shopItemId: Int) {
        viewModelScope.launch {
            val item = getShopItemUseCase.getShopItem(shopItemId)
            _shopItemLD.value = item
        }
    }

    fun addShopItem(name: String?, count: String?) {
        val parseName = parseName(name)
        val parseCount = parseCount(count)
        val validate = validateInput(parseName, parseCount)
        if (validate) {
            viewModelScope.launch {
                addShopItemUseCase.addShopItem(
                    ShopItem(parseName, parseCount, true)
                )
                finishWork()
            }
        }
    }

    fun editShopItem(name: String?, count: String?) {
        val parseName = parseName(name)
        val parseCount = parseCount(count)
        val validate = validateInput(parseName, parseCount)
        if (validate) {
            _shopItemLD.value?.let {
                viewModelScope.launch {
                    val item = it.copy(name = parseName, count = parseCount)
                    editShopItemUseCase.editShopItem(item)
                    finishWork()
                }
            }
        }
    }

    private fun parseName(name: String?): String {
        return name?.trim() ?: ""
    }

    private fun parseCount(count: String?): Int {
        return try {
            count?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var res = true
        if (name.isBlank()) {
            _errorInputName.value = true
            res = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            res = false
        }
        return res
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _closeScreenLD.value = Unit
    }
}