package com.sample.openweathermap

import android.app.Application
import androidx.work.Configuration
import com.sample.openweathermap.data.workmanager.MyWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var myWorkerFactory: MyWorkerFactory

    /*
    * Custom configuration of Work Manger
    */
    override fun getWorkManagerConfiguration(): Configuration {

        return Configuration.Builder()
            .setWorkerFactory(myWorkerFactory)
            .build()

    }
}