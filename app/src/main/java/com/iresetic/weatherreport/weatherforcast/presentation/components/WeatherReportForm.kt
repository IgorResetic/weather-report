package com.iresetic.weatherreport.weatherforcast.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.weatherforcast.presentation.model.UiCityWeatherReport

@Composable
fun WeatherReportForm(
    weatherReport: UiCityWeatherReport
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),

    ) {
        Text(
            modifier = Modifier
                .align(alignment = CenterHorizontally)
                .padding(top = 32.dp, bottom = 32.dp),
            text = weatherReport.cityName,
            fontSize = 25.sp
        )
        WeatherReportDataElement(
            type = stringResource(id = R.string.weather_forecast_temperature_title),
            data = weatherReport.currentTemperature
        )
        WeatherReportDataElement(
            type = stringResource(id = R.string.weather_forecast_humidity_title),
            data = weatherReport.humidity
        )
        WeatherReportDataElement(
            type = stringResource(id = R.string.weather_forecast_wind_speed_title),
            data = weatherReport.windSpeed
        )
    }
}