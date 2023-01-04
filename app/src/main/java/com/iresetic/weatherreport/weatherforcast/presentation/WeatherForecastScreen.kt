package com.iresetic.weatherreport.weatherforcast.presentation

import android.annotation.SuppressLint
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.iresetic.weatherreport.core.presentation.routes.Destination

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherForecastScreen(
    navController: NavController
) {
    Scaffold() {
        Button(onClick = {
            navController.navigate(Destination.LocationSelector.route)
        }) {
            Text(text = "Location")
        }
    }
}