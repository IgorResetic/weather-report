package com.iresetic.weatherreport.weatherforcast.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.core.presentation.routes.Destination
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<WeatherForecastViewModel>()

    LaunchedEffect(Unit) {
        this.launch(Dispatchers.IO) {
            viewModel.onEvent(WeatherForecastEvent.GetCityWeatherReport)
        }
    }

    Scaffold() {
        val viewModelState = viewModel.state

        Column {
            if(viewModelState.isLoading) {
                // TODO Extract to a separate component as it's used in LocationSelectorScreen
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                Column {
                    val weatherReport = viewModel.state.weatherReport
                    if(weatherReport != null) {
                        Text(text = weatherReport.cityName)
                        Text(text = "${weatherReport.currentTemperature.value} " +
                            weatherReport.currentTemperature.measuringUnit)
                        Text(text = "${weatherReport.humidity.value} ${weatherReport.humidity.measuringUnit}")
                        Text(text = "${weatherReport.windSpeed.value} ${weatherReport.windSpeed.measuringUnit}")
                    }
                }
            }

            Button(onClick = {
                navController.navigate(Destination.LocationSelector.route)
            }) {
                Text(text = "Location")
            }
        }
    }
}