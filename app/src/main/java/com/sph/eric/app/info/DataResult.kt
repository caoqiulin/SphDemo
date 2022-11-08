package com.sph.eric.app.info

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/8
 */
sealed class DataResult <out T : Any> {
    class Success<out T : Any>(val data: T) : DataResult<T>()
    class Error(val exception: Throwable) : DataResult<Nothing>()
}