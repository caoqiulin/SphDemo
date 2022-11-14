package com.sph.eric.db.record

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sph.eric.db.converter.RecordConverter
import com.sph.eric.model.Record
import kotlinx.coroutines.flow.Flow

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/13
 */
@Dao
interface RecordDAO {

    @Query("SELECT * FROM record_list")// ORDER BY quarter DESC
    fun getRecordList() : List<Record>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: Record)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun insertList(list: List<Record>)

    @Query("DELETE FROM record_list")
    suspend fun deleteAll()

}