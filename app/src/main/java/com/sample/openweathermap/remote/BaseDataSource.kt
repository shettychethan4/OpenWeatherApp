package com.sample.openweathermap.remote

import retrofit2.Response

abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): RemoteSource<T> {

        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return RemoteSource.Success(body)
            }
            return RemoteSource.Failure(false, response.code(), response.errorBody())
        } catch (e: Exception) {
            return RemoteSource.Failure(true, null, null)
        }
    }

}