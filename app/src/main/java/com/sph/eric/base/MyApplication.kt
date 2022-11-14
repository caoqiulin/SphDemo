package com.sph.eric.base

import android.app.Application
import com.sph.eric.db.RecordRoomDatabase
import com.sph.eric.db.record.RecordDBRepository
import com.sph.eric.di.netWorkModule
import com.sph.eric.util.GlobalContext
import com.sph.eric.util.application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class MyApplication: Application() {

    private val database by lazy { RecordRoomDatabase.getDatabase() }
    val recordDBRepository by lazy { RecordDBRepository(database.recordDao()) }

    override fun onCreate() {
        super.onCreate()
        GlobalContext = this
        application = this
        // init koin
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }
    }

    private val appModule = listOf(netWorkModule)

}