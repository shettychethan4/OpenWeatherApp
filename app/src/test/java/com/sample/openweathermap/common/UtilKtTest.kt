package com.sample.openweathermap.common

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class UtilKtTest {

    @Test
    fun getTime_validFormat_outputMatched() {
        val output = "6:13 AM"
        val intFormat = 1617497027
        val result = intFormat.getTime()
        assertThat(result).isEqualTo(output)
    }

    @Test
    fun getTime_inValidFormat_outputNotMatched() {
        val output = "6:13 AM"
        val intFormat = 161749702
        val result = intFormat.getTime()
        assertThat(result).isNotEqualTo(output)
    }

    @Test
    fun getTemperatureInString_validInput_outputMatched() {
        val output = "34"
        val temperatureInKelvin = 307.18
        val result = temperatureInKelvin.getTemperatureInString()
        assertThat(result).isEqualTo(output)
    }

    @Test
    fun getTemperatureInString_validInputNegativeResult_outputMatched() {
        val output = "-173"
        val temperatureInKelvin = 100.18
        val result = temperatureInKelvin.getTemperatureInString()
        assertThat(result).isEqualTo(output)
    }

    @Test
    fun getTemperatureInString_inValidInput_outputNotMatched() {
        val output = "0"
        val temperatureInKelvin = 357.18
        val result = temperatureInKelvin.getTemperatureInString()
        assertThat(result).isNotEqualTo(output)
    }

}