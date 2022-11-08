package com.sph.eric.di

import com.sph.eric.http.ApiRepository
import org.koin.dsl.module

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
val api = ApiRepository.api

val netWorkModule = module {
    single {
        api
    }
}