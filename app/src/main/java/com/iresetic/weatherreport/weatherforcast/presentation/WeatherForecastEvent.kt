package com.iresetic.weatherreport.weatherforcast.presentation

sealed interface WeatherForecastEvent {
    object GetCityWeatherReport: WeatherForecastEvent
}