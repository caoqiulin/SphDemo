package com.sph.eric.http

import retrofit2.HttpException
import java.net.SocketTimeoutException

/**
 * @Author: 曹秋淋
 * @Date: 2022/11/9
 */
sealed class RequestResult<out T : Any> {
    class Success<out T : Any>(val result : T) : RequestResult<T>()
    class Error(val exception: Throwable) : RequestResult<Nothing>() {
        val isConnectionFailure: Boolean
            get() = exception is HttpException || exception is SocketTimeoutException
    }
    object Cancel : RequestResult<Nothing>()
}