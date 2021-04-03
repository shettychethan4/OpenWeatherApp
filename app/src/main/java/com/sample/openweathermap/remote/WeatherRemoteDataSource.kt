package com.sample.openweathermap.remote

import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    BaseDataSource() {

    suspend fun fetchWeatherData(
        apiKey: String,
        latitude: String,
        longitude: String
    ): RemoteSource<WeatherResponse> {
        return getResult { apiService.getWeatherByLocation(latitude, longitude, apiKey) }
    }
}