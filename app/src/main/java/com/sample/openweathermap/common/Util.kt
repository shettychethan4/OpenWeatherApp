package com.sample.openweathermap.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.view.View
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

object ConnectivityUtil {
    fun isConnected(context: Context?): Boolean {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

/*
* Metric Unit is not considered
*/
fun Double.getTemperatureString(): String {
    return (this - KELVIN).roundToInt().toString()
}

/**
 * Get formatted local time for the sunrise/sunset
 */
fun Int.getTime(): String? {
    val date = Date(this * 1000L)
    val sdf = SimpleDateFormat("h:mm a", Locale.getDefault())
    sdf.timeZone = TimeZone.getDefault()
    return sdf.format(date)
}


