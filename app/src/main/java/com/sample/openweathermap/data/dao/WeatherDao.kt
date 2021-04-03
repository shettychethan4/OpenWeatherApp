package com.sample.openweathermap.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.openweathermap.remote.WeatherResponse

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherResponse)

    @Query("SELECT * FROM WeatherResponse ORDER BY id DESC LIMIT 1")
    suspend fun getWeather(): WeatherResponse


}