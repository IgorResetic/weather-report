package com.iresetic.weatherreport.weatherforcast.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iresetic.weatherreport.weatherforcast.domain.model.WeatherData

@Composable
fun WeatherReportDataElement(
    type: String,
    data: WeatherData,
) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 8.dp)) {
        Text(text = "$type: ", fontSize = 16.sp)
        Text(text = "${data.value} ${data.measuringUnit}", fontSize = 16.sp)
    }
}
