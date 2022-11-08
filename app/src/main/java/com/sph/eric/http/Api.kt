package com.sph.eric.http

import com.sph.eric.base.BaseModel
import com.sph.eric.model.MainData
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
interface Api {

    @GET("https://data.gov.sg/dataset/mobile-data-usage")
    fun getMainDataAsync() : BaseModel<List<MainData>>
}