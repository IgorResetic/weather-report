package com.iresetic.weatherreport.weatherforcast.domain.model

data class WeatherData(
    val value: String,
    val measuringUnit: String
) {
    companion object {
        fun emptyWeatherData() = WeatherData(
            value = "",
            measuringUnit = ""
        )
    }
}
