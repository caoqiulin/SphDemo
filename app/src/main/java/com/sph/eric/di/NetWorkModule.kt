package com.sph.eric.di

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.sph.eric.BuildConfig
import com.sph.eric.http.ApiService
import com.sph.eric.repository.MainDataRepository
import com.sph.eric.repository.MainDataRepositoryImpl
import com.sph.eric.viewmodel.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.util.concurrent.TimeUnit


/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
val netWorkModule = module {
    single {
        createWebService(
            okHttpClient = createHttpClient(),
            factory = RxJava2CallAdapterFactory.create(),
            baseUrl = BuildConfig.BASE_URL
        ).create(ApiService::class.java)
    }
    // create an instance
    factory<MainDataRepository> { MainDataRepositoryImpl(api = get()) }
    // build MainViewModel
    viewModel { MainViewModel(repository = get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
    val client = OkHttpClient.Builder()
    client.readTimeout(5 * 60, TimeUnit.SECONDS)
    if (BuildConfig.DEBUG) {
        if (Timber.treeCount == 0)
            Timber.plant(Timber.DebugTree())
        client.addInterceptor(HttpLoggingInterceptor { message ->
            try {
                val prettyMessage = when {
                    message.startsWith("{") && message.endsWith("}") ->
                        JSONObject(message).toString(2)
                    message.startsWith("[") && message.endsWith("]") ->
                        JSONArray(message).toString(2)
                    else -> message
                }
                Timber.tag("OkHttp").d(prettyMessage)
            } catch (e: JSONException) {
                Timber.tag("OkHttp").e(e)
            }
        }.setLevel(HttpLoggingInterceptor.Level.BODY))
    }

    return client.addInterceptor {
        val original = it.request()
        val requestBuilder = original.newBuilder()
        requestBuilder.header("Content-Type", "application/json")
        val request = requestBuilder.method(original.method, original.body).build()
        return@addInterceptor it.proceed(request)
    }.build()
}

/* function to build our Retrofit service */
fun createWebService(
    okHttpClient: OkHttpClient,
    factory: CallAdapter.Factory, baseUrl: String
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .addCallAdapterFactory(factory)
        .client(okHttpClient)
        .build()
}