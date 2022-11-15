package com.sph.eric.db.record

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.sph.eric.model.Record
import kotlinx.coroutines.flow.Flow

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/13
 */
class RecordDBRepository(private val recordDAO: RecordDAO) {

    val recordList : List<Record> = recordDAO.getRecordList()

    @WorkerThread
    suspend fun insertRecord(list: List<Record>) {
        recordDAO.insertList(list)
    }

    @WorkerThread
    suspend fun insert(record: Record) {
        recordDAO.insert(record)
    }

    @WorkerThread
    fun getRecordByYear(year: String): List<Record> {
        return recordDAO.getRecordByYear(year = year)
    }

    @WorkerThread
    fun getYearList(): List<String> {
        return recordDAO.getYearList()
    }

    @WorkerThread
    suspend fun deleteAll() {
        recordDAO.deleteAll()
    }
}