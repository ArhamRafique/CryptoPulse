package com.muhammad.crypto

import android.app.Application
import com.muhammad.crypto.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CryptoApplication : Application(){
    companion object{
        lateinit var INSTANCE : CryptoApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        startKoin {
            androidContext(this@CryptoApplication)
            androidLogger()
            modules(appModule)
        }
    }
}