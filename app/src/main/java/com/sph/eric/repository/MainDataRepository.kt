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
    suspend fun getMainDataList() : RequestResult<BaseModel<MainDataModel>>
}

class MainDataRepositoryImpl(private val api: ApiService) : MainDataRepository{
    override suspend fun getMainDataList() : RequestResult<BaseModel<MainDataModel>> {
        return try {
            RequestResult.Success(api.getMainDataAsync("a807b7ab-6cad-4aa6-87d0-e283a7353a0f").await())
        } catch (e:Exception) {
            RequestResult.Error(e)
        }
    }
}