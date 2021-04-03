package com.sample.openweathermap.data

import com.sample.openweathermap.common.apiKey
import com.sample.openweathermap.data.dao.WeatherDao
import com.sample.openweathermap.remote.RemoteSource
import com.sample.openweathermap.remote.WeatherRemoteDataSource
import com.sample.openweathermap.remote.WeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherDataSource @Inject constructor(
    private val dataSource: WeatherRemoteDataSource,
    private val dao: WeatherDao
) {

    suspend fun fetchData(latitude: String, longitude: String): RemoteSource<WeatherResponse> {
        return withContext(Dispatchers.IO) {
            val response = dataSource
                .fetchWeatherData(apiKey = apiKey, latitude, longitude)
            when (response) {
                is RemoteSource.Success -> {
                    val result = response.value
                    dao.insert(result)
                }
            }

            response
        }

    }

}