package com.iresetic.weatherreport.core.presentation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorScreen
import com.iresetic.weatherreport.weatherforcast.presentation.WeatherForecastScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destination.WeatherForecast.route
    ) {
        composable(
            route = Destination.WeatherForecast.route
        ) {
            WeatherForecastScreen(navController)
        }

        composable(
            route = Destination.LocationSelector.route
        ) {
            LocationSelectorScreen(navController)
        }
    }
}
