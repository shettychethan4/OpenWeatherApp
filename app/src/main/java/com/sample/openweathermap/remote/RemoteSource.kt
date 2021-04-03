package com.sample.openweathermap.remote

import okhttp3.ResponseBody

sealed class RemoteSource<out T> {

    data class Success<out T>(val value: T) : RemoteSource<T>()

    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : RemoteSource<Nothing>()

    object Loading : RemoteSource<Nothing>()

}
