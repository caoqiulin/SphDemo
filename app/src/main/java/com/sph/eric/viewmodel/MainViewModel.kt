package com.sph.eric.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sph.eric.base.BaseModel
import com.sph.eric.http.RequestResult
import com.sph.eric.http.SingleLiveEvent
import com.sph.eric.model.MainDataModel
import com.sph.eric.repository.MainDataRepository
import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class MainViewModel(private val repository: MainDataRepository):ViewModel(), CoroutineScope {
    private val job = Job()

    var data = MutableLiveData<BaseModel<MainDataModel>>()
    var showError = SingleLiveEvent<String>()

    fun loadData() {
        launch {
            val result = withContext(Dispatchers.IO) {repository.getMainDataList()}
            when (result) {
                is RequestResult.Success -> {
                    // notify adapter observe and load next page
                    data.value = result.result
//                    Timber.tag("okHttp").d("返回数据：${data.value}")
                }
                is RequestResult.Error -> {
                    // end load
                    showError.value = result.exception.message
                    Timber.tag("okHttp").d("请求失败：${showError.value}")
                }
                else -> {}
            }
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}