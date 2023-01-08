package com.iresetic.weatherreport.weatherforcast.presentation.model

import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.model.WeatherData

data class UiCityWeatherReport(
    val cityName: String,
    val currentTemperature: WeatherData,
    val humidity: WeatherData,
    val windSpeed: WeatherData
) {
    companion object {
        fun fromDomain(cityWeatherReport: CityWeatherReport) = UiCityWeatherReport(
            cityName = cityWeatherReport.cityName,
            currentTemperature = cityWeatherReport.currentTemperature,
            humidity = cityWeatherReport.humidity,
            windSpeed = cityWeatherReport.windSpeed
        )

        fun emptyUiCityWeatherReport() = UiCityWeatherReport(
            cityName = "",
            currentTemperature = WeatherData.emptyWeatherData(),
            humidity = WeatherData.emptyWeatherData(),
            windSpeed = WeatherData.emptyWeatherData()
        )
    }
}