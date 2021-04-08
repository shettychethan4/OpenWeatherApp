package com.sample.openweathermap.data.workmanager

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.sample.openweathermap.common.SharedPreferencesRepository
import com.sample.openweathermap.data.WeatherDataSource
import javax.inject.Inject

/*
* Factory created to provide dependency to Work Manager
*/
class MyWorkerFactory @Inject constructor(
    private val weatherDataSource: WeatherDataSource,
    private val preferences: SharedPreferencesRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return MyWorkManager(appContext, workerParameters, weatherDataSource, preferences)
    }
}