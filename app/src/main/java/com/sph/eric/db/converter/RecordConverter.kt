package com.sph.eric.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sph.eric.model.Record
import java.lang.reflect.Type

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/14
 */
class RecordConverter {

    @TypeConverter
    fun fromRecordList(list: List<Record>): String? {
        if (list.isEmpty()) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Record?>?>() {}.type
        return gson.toJson(list, type)
    }

    @TypeConverter
    fun toRecordList(json: String): List<Record>? {
        if (json.isEmpty()) return null
        val gson = Gson()
        val type: Type = object : TypeToken<List<Record?>?>() {}.type
        return gson.fromJson(json, type)
    }
}