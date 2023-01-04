package com.iresetic.weatherreport.locationselection.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.core.presentation.routes.Destination
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.GetCities
import com.iresetic.weatherreport.locationselection.presentation.components.CityListItem
import com.iresetic.weatherreport.ui.theme.BlackTransparent_15
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectorScreen(navController: NavController) {
    val viewModel = hiltViewModel<LocationSelectorViewModel>()

    LaunchedEffect(Unit){
        this.launch(Dispatchers.IO) {
            viewModel.onEvent(GetCities)
        }
    }

    Scaffold() {
        Column() {
            Text(text = "${viewModel.state.isLoading} ::: ${viewModel.state.cities.size }")
            LazyColumn() {
                val viewModelState = viewModel.state
                val numberOfCities = viewModelState.cities.size

                items(numberOfCities) { index ->
                    val cityName = viewModelState.cities[index].cityName
                    CityListItem(cityName = cityName)
                    Divider(color = BlackTransparent_15, thickness = 0.8.dp)
                }
                item {
                    if(viewModelState.isLoading) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

            }
            Button(onClick = { navController.navigate(Destination.WeatherForecast.route) }) {
                Text(text = "Weather forecast")
            }
        }
    }
}
