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

        /* latitude?.let { it1 ->
             longitude?.let {
                 it2 ->
                 withContext(Dispatchers.IO) {
                     weatherDataSource.fetchData(it1, it2)
                     return@withContext Result.success()
                 }

             }
         }*/



        return if (latitude?.isNotEmpty() == true && longitude != null)
            withContext(Dispatchers.IO) {
                weatherDataSource.fetchData(latitude, longitude)
                return@withContext Result.success()
            }
        else
            Result.failure()
    }
}