package com.sph.eric.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // init koin
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private val appModule = module{
        // more dependices
    }

}