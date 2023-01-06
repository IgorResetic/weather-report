package com.iresetic.weatherreport.weatherforcast.domain.model

data class CityWeatherReport(
    val id: String,
    val cityName: String,
    val currentTemperature: WeatherData,
    val humidity: WeatherData,
    val windSpeed: WeatherData
) {
    companion object {
        fun emptyCityWeatherReport() = CityWeatherReport(
            id = "",
            cityName = "",
            currentTemperature = WeatherData.emptyWeatherData(),
            humidity = WeatherData.emptyWeatherData(),
            windSpeed = WeatherData.emptyWeatherData()
        )
    }
}
