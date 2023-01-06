package com.iresetic.weatherreport.weatherforcast.presentation

import com.iresetic.weatherreport.weatherforcast.presentation.model.UiCityWeatherReport

data class WeatherForecastUiState(
    val isLoading: Boolean = false,
    val weatherReport: UiCityWeatherReport? = null,
    val error: String? = null
)
