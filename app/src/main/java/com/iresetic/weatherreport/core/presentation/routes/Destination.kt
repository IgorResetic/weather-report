package com.iresetic.weatherreport.core.presentation.routes

sealed class Destination(val route: String) {
    object WeatherForecast : Destination(route = "weather_forecast_screen")
    object LocationSelector : Destination(route = "location_selector_screen")
}
