package com.sample.openweathermap.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/data/2.5/weather")
    suspend fun getWeatherByLocation(
        @Query("lat") lat: String? = null,
        @Query("lon") long: String? = null,
        @Query("apiKey") apiKey: String
    ): Response<WeatherResponse>

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/"
    }
}