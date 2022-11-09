package com.sph.eric.repository

import com.sph.eric.base.BaseModel
import com.sph.eric.http.ApiService
import com.sph.eric.http.RequestResult
import com.sph.eric.model.MainDataModel

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
interface MainDataRepository {
    suspend fun getMainDataList() : RequestResult<BaseModel<List<MainDataModel>>>
}

class MainDataRepositoryImpl(private val api: ApiService) : MainDataRepository{
    override suspend fun getMainDataList() : RequestResult<BaseModel<List<MainDataModel>>> {
        return try {
            RequestResult.Success(api.getMainDataAsync().await())
        } catch (e:Exception) {
            RequestResult.Error(e)
        }
    }
}