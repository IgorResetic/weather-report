package com.iresetic.weatherreport.weatherforcast.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.core.presentation.routes.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<WeatherForecastViewModel>()

    Scaffold() {
        viewModel.getCity()
        Button(onClick = {
            navController.navigate(Destination.LocationSelector.route)
        }) {
            Text(text = "Location")
        }
    }
}