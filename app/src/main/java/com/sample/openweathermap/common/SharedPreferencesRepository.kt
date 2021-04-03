package com.sample.openweathermap.common

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesRepository(context: Context) {

    companion object {
        const val SHARED_PREFERENCES = "shared_preferences"
        const val KEY_LATITUDE = "latitude"
        const val KEY_LONGITUDE = "longitude"
    }


    private var mySharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES, Context.MODE_PRIVATE
    )
    private lateinit var editor: SharedPreferences.Editor


    fun setLatitude(key: String, value: String) {
        editor = mySharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun setLongitude(key: String, value: String) {
        editor = mySharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }


    fun getLatitude(): String? {
        return mySharedPreferences.getString(KEY_LATITUDE, "")
    }

    fun getLongitude(): String? {
        return mySharedPreferences.getString(KEY_LONGITUDE, "")
    }


    fun clearInfo() {
        editor = mySharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}