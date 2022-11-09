package com.sph.eric.http

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 */
sealed class RequestResult<out T : Any> {
    class Success<out T : Any>(val data : T) : RequestResult<T>()
    class Error(val exception: Throwable) : RequestResult<Nothing>()
}