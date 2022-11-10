package com.sph.eric.base

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
data class BaseModel<T>(val help:String, val success:Boolean, val result: T)