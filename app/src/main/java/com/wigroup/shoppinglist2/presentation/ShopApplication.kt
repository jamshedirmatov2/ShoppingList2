package com.wigroup.shoppinglist2.presentation

import android.app.Application
import com.wigroup.shoppinglist2.di.DaggerApplicationComponent

class ShopApplication : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}