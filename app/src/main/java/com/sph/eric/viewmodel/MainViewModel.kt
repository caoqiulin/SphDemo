package com.sph.eric.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sph.eric.model.MainData
import com.sph.eric.repository.MainDataRepository
import kotlinx.coroutines.*

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
class MainViewModel(private val repository: MainDataRepository):ViewModel() {

    private val dataList = MutableLiveData<List<MainData>?>()

    fun loadData() {
        viewModelScope.launch {
            dataList.value = repository.getMainDataList().data
        }
    }

}