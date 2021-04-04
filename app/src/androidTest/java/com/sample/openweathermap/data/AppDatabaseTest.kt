package com.sample.openweathermap.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.sample.openweathermap.data.dao.WeatherDao
import com.sample.openweathermap.remote.*
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {


    private lateinit var db: AppDatabase
    private lateinit var dao: WeatherDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = db.getWeatherDao()
    }


    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeAndRead() = runBlocking(Dispatchers.IO) {

        val mainObj = Main(
            temp = 305.48,
            feelsLike = 305.04,
            tempMin = 304.82,
            tempMax = 306.15,
            pressure = 1014,
            humidity = 35
        )

        val sysObj = Sys(
            type = 1,
            id = null,
            country = "In",
            sunrise = 1617497026,
            sunset = 1617541265
        )

        val cordObj = Coord(
            lon = 77.5996,
            lat = 12.902
        )

        val windObj = Wind(
            deg = 340,
            speed = 4.12
        )

        val weatherResponse = WeatherResponse(
            visibility = 0,
            timezone = 19800,
            main = mainObj,
            clouds = null,
            sys = sysObj,
            dt = 0,
            coord = cordObj,
            weather = null,
            name = "Bengaluru",
            cod = 0,
            base = null,
            wind = windObj,
            id = 1
        )

        dao.insert(weatherResponse)
        val result = dao.getWeather()
        assertThat(result.equals(weatherResponse)).isTrue()
    }

}