package com.sph.eric.model

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

data class Record(
    val volume_of_mobile_data: String,
    val quarter: String,
    val _id: Int
)

data class Link(
    val start: String,
    val next: String
)
