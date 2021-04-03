package com.sample.openweathermap.di

import android.app.Application
import androidx.work.WorkManager
import com.google.gson.Gson
import com.sample.openweathermap.BuildConfig
import com.sample.openweathermap.common.SharedPreferencesRepository
import com.sample.openweathermap.data.AppDatabase
import com.sample.openweathermap.data.WeatherDataSource
import com.sample.openweathermap.data.workmanager.MyWorkerFactory
import com.sample.openweathermap.remote.ApiService
import com.sample.openweathermap.remote.WeatherRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherService(
        @WeatherApi okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, ApiService::class.java)

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory, clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(weatherService: ApiService) =
        WeatherRemoteDataSource(weatherService)

    @WeatherApi
    @Provides
    fun providePrivateOkHttpClient(
        upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().build()
    }

    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(interceptor)
            .build()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)


    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()


    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiService.BASE_URL)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideSharedPreference(app: Application) =
        SharedPreferencesRepository(app.applicationContext)

    @Singleton
    @Provides
    fun provideWorkerFactory(
        weatherDataSource: WeatherDataSource,
        preference: SharedPreferencesRepository
    ) = MyWorkerFactory(weatherDataSource, preference)

    @Singleton
    @Provides
    fun provideWorkManager(app: Application) = WorkManager.getInstance(app.applicationContext)

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideNewsSetDao(db: AppDatabase) = db.getWeatherDao()

}