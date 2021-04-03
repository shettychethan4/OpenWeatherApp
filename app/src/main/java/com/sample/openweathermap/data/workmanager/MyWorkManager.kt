package com.sample.openweathermap.data.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sample.openweathermap.common.SharedPreferencesRepository
import com.sample.openweathermap.data.WeatherDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class MyWorkManager constructor(
    context: Context,
    workerParameters: WorkerParameters,
    private val weatherDataSource: WeatherDataSource,
    private val pref: SharedPreferencesRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val latitude = pref.getLatitude()
        val longitude = pref.getLongitude()
        return if (latitude != "" && longitude != "")
            withContext(Dispatchers.IO) {
                weatherDataSource.fetchData(latitude!!, longitude!!)
                return@withContext Result.success()
            }
        else
            Result.failure()
    }
}