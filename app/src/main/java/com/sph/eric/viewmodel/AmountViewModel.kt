package com.sph.eric.viewmodel

import androidx.lifecycle.*
import com.sph.eric.db.record.RecordDBRepository
import com.sph.eric.model.Record
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/14
 */
class AmountViewModel(private val recordDBRepository: RecordDBRepository) : ViewModel(),
    CoroutineScope {
    private val job = Job()

    var recordListByYear: MutableLiveData<List<Record>> = MutableLiveData()
    var yearList: MutableLiveData<List<String>> = MutableLiveData()

    fun loadData(){
        launch {
            val listY =  withContext(Dispatchers.IO) {recordDBRepository.getYearList() }
            yearList.value = listY
        }
    }

    fun loadDataByYear(year: String) {
        launch {
            val listR =  withContext(Dispatchers.IO) {recordDBRepository.getRecordByYear(year) }
            recordListByYear.value = listR
        }
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}

class AmountViewModelFactory(private val recordDBRepository: RecordDBRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AmountViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AmountViewModel(recordDBRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}