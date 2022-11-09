package com.sph.eric.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    var dataList = MutableLiveData<List<MainDataModel>?>()
    var showError = SingleLiveEvent<String>()

    fun loadData() {
        launch {
            val result = withContext(Dispatchers.IO) {repository.getMainDataList()}
            when (result) {
                is RequestResult.Success -> {
                    // notify adapter observe and load next page
                    dataList.value = result.data.data
                    Timber.tag("Http").e("json data ==> ${dataList.value}")
                }
                is RequestResult.Error -> {
                    // end load
                    showError.value = result.exception.message
                    Timber.tag("Http").e(showError.value)
                }
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