package com.sph.eric.db.record

import androidx.annotation.WorkerThread
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

    suspend fun insert(record: Record) {
        recordDAO.insert(record)
    }

    @WorkerThread
    suspend fun deleteAll() {
        recordDAO.deleteAll()
    }
}