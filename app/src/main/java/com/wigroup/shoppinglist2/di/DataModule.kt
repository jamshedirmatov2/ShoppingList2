package com.wigroup.shoppinglist2.di

import android.app.Application
import com.wigroup.shoppinglist2.data.AppDatabase
import com.wigroup.shoppinglist2.data.ShopListDao
import com.wigroup.shoppinglist2.data.ShopListRepositoryImpl
import com.wigroup.shoppinglist2.domain.ShopListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindShopListRepository(impl: ShopListRepositoryImpl): ShopListRepository

    companion object {

        @ApplicationScope
        @Provides
        fun provideShopListDao(application: Application): ShopListDao =
            AppDatabase.getInstance(application).shopListDao()
    }
}