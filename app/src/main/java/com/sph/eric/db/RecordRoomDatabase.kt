package com.sph.eric.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.sph.eric.db.converter.RecordConverter
import com.sph.eric.db.record.RecordDAO
import com.sph.eric.model.Record
import com.sph.eric.util.GlobalContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/13
 */
@Database(entities = [Record::class], version = 2, exportSchema = false)
abstract class RecordRoomDatabase: RoomDatabase() {

    abstract fun recordDao() : RecordDAO



    companion object {
        @Volatile
        private var INSTANCE: RecordRoomDatabase? = null
        fun getDatabase(): RecordRoomDatabase {
            return INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    GlobalContext,
                    RecordRoomDatabase::class.java,
                    "record_database.db"
                )
//                    .addCallback(SupportSQLiteDatabase())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

