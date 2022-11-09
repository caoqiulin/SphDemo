package com.sph.eric.base

import android.app.Application
import com.sph.eric.di.mainRepoModule
import com.sph.eric.di.netWorkModule
import com.sph.eric.util.GlobalContext
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
        GlobalContext = this
        // init koin
        startKoin {
            androidContext(this@MyApplication)
            modules(netWorkModule)
        }
    }

    private val appModule = module{
        netWorkModule
//        mainRepoModule
    }

}