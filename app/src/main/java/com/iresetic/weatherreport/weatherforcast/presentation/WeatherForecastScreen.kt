package com.iresetic.weatherreport.weatherforcast.presentation

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.core.presentation.components.ProgressIndicator
import com.iresetic.weatherreport.core.presentation.routes.Destination
import com.iresetic.weatherreport.weatherforcast.presentation.components.WeatherReportForm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun WeatherForecastScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<WeatherForecastViewModel>()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.state.isRefreshing,
        onRefresh = { viewModel.onEvent(WeatherForecastEvent.RefreshWeatherForecastScreen) })

    LaunchedEffect(Unit) {
        this.launch(Dispatchers.IO) {
            viewModel.onEvent(WeatherForecastEvent.GetCityWeatherReport)
        }
    }

    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        val viewModelState = viewModel.state
        if(viewModelState.isLoading) {
            ProgressIndicator()
        } else {

            if(viewModelState.error != null) {
                Toast.makeText(LocalContext.current, viewModel.state.error, Toast.LENGTH_SHORT).show()
            }

            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
            ) {

                when {
                    !viewModelState.isCitySelected -> {
                        Text(text = stringResource(id = R.string.weather_forecast_city_not_selected_message))
                    }

                    else -> {
                        val weatherReport = viewModel.state.weatherReport!!
                        WeatherReportForm(weatherReport = weatherReport)
                    }
                }

                Button(
                    modifier = Modifier.align(alignment = CenterHorizontally),
                    onClick = {
                        navController.navigate(Destination.LocationSelector.route)
                    }
                ) {
                    Text(text = stringResource(id = R.string.weather_forecast_select_a_city_button))
                }
            }
        }

        PullRefreshIndicator(
            viewModelState.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )
    }
}