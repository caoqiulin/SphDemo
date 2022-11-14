package com.sph.eric.di

import com.sph.eric.db.RecordRoomDatabase
import com.sph.eric.db.record.RecordDBRepository
import com.sph.eric.viewmodel.RecordViewModel
import com.sph.eric.viewmodel.RecordViewModelFactory
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/14
 */

val recordModule = module (override = true) {
    factory{ RecordViewModel(get()) }
    viewModel { RecordViewModel(recordDBRepository = get()) }
}