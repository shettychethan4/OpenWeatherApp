package com.sample.openweathermap.common

import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

/*
* Metric Unit is not considered
*/
fun Double.getTemperatureInString(): String {
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