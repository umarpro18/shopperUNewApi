package com.sample.shopperu

import android.app.Application
import com.sample.data.di.dataModule
import com.sample.domain.di.domainModule
import com.sample.shopperu.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopperUAppNewApi : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ShopperUAppNewApi)
            modules(listOf(
                presentationModule,
                domainModule,
                dataModule
            ))
        }

    }
}