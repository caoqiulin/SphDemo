package com.sph.eric.repository

import com.sph.eric.base.BaseModel
import com.sph.eric.http.Api
import com.sph.eric.model.MainData

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class MainDataRepository(private val api: Api) {
    suspend fun getMainDataList() : BaseModel<List<MainData>> = api.getMainDataAsync()
}