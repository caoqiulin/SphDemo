package com.sph.eric.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.sph.eric.db.converter.RecordConverter

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
data class MainDataModel(
    val resource_id: String,
    val fields: List<Field>,
    val records: List<Record>,//main data
    val _link: Link,
    val limit: Int,
    val total: Int
)

data class Field(
    val type: String,
    val id: String
)

@Entity(tableName = "record_list")
@TypeConverters(RecordConverter::class)
data class Record(
    @PrimaryKey @ColumnInfo(name = "id")
    val _id: Int,
    @ColumnInfo(name = "data")
    val volume_of_mobile_data: String,
    @ColumnInfo(name = "quarter")
    val quarter: String,
    @ColumnInfo(name = "year")
    var year: String
)

data class Link(
    val start: String,
    val next: String
)
