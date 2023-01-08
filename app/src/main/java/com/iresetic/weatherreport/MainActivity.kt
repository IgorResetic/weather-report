package com.iresetic.weatherreport

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.iresetic.weatherreport.core.presentation.routes.SetupNavGraph
import com.iresetic.weatherreport.locationselection.domain.usecases.GetAllCities
import com.iresetic.weatherreport.locationselection.domain.usecases.GetCityData
import com.iresetic.weatherreport.ui.theme.WeatherReportTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var getAllCities: GetAllCities
    @Inject lateinit var getCityData: GetCityData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            WeatherReportTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}
