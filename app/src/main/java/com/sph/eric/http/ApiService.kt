package com.sph.eric.http

import com.sph.eric.base.BaseModel
import com.sph.eric.model.MainDataModel
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 */
interface ApiService {
    @GET("datastore_search")
    fun getMainDataAsync(@Query("resource_id") resource_id:String) : Deferred<BaseModel<MainDataModel>>
}