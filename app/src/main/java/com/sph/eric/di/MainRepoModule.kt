package com.sph.eric.di

import com.sph.eric.repository.MainDataRepositoryImpl
import org.koin.dsl.module

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */

val mainRepoModule = module {
    single {

        MainDataRepositoryImpl(get())
    }
}

