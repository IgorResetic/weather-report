package com.iresetic.weatherreport.weatherforcast.presentation

import com.iresetic.weatherreport.weatherforcast.presentation.model.UiCityWeatherReport

data class WeatherForecastUiState(
    val isLoading: Boolean = true,
    val weatherReport: UiCityWeatherReport? = null,
    val isCitySelected: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null
)
