package com.sph.eric.viewmodel

import androidx.lifecycle.*
import com.sph.eric.db.record.RecordDBRepository
import com.sph.eric.model.Record
import kotlinx.coroutines.launch

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/13
 */
class RecordViewModel(private val recordDBRepository: RecordDBRepository): ViewModel(){
    val recordList: List<Record> = recordDBRepository.recordList

    fun insertList(list: List<Record>) = viewModelScope.launch {
        recordDBRepository.deleteAll()
//        recordDBRepository.insertRecord(list)
        for (record in list) {
            record.year = record.quarter.substring(0,4)
            recordDBRepository.insert(record = record)
        }
    }

}

class RecordViewModelFactory(private val recordDBRepository: RecordDBRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RecordViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RecordViewModel(recordDBRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class")
    }
}