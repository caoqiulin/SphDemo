package com.sph.eric.net

import com.sph.eric.BuildConfig
import com.sph.eric.di.createHttpClient
import com.sph.eric.di.createWebService
import com.sph.eric.http.ApiService
import com.sph.eric.repository.MainDataRepositoryImpl
import com.sph.eric.viewmodel.MainViewModel
import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/15
 */
@RunWith(MockitoJUnitRunner::class)
class NetWorkTest {

    @Test
    fun testRetrofitInstance() {
        val instance: Retrofit = createWebService(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = BuildConfig.BASE_URL
        )
        assert(instance.baseUrl().toUrl().toString() == BuildConfig.BASE_URL)
    }

    @Test
    fun testApiService() = runBlocking {
        val instance: Retrofit = createWebService(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = BuildConfig.BASE_URL
        )
        val api: ApiService = instance.create(ApiService::class.java)
        val viewModelImpl = MainDataRepositoryImpl(api)
        val viewModel = MainViewModel(viewModelImpl)
        viewModel.loadData()

        Assert.assertNotNull(viewModel.data)

    }
}