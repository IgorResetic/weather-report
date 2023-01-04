package com.iresetic.weatherreport.locationselection.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.GetCities
import com.iresetic.weatherreport.locationselection.presentation.components.CityListItem
import com.iresetic.weatherreport.locationselection.presentation.components.SearchTopBar
import com.iresetic.weatherreport.ui.theme.BlackTransparent_15
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationSelectorScreen(navController: NavController) {
    val viewModel = hiltViewModel<LocationSelectorViewModel>()
    val focusManager = LocalFocusManager.current
    val locationSelectorState = rememberLocationSelectorState()

    LaunchedEffect(Unit){
        this.launch(Dispatchers.IO) {
            viewModel.onEvent(GetCities)
        }
    }

    Scaffold(
        topBar = { SearchTopBar(navController, locationSelectorState) },
        content = {
            LazyColumn(
                state = locationSelectorState.listState,
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                ) {
                val viewModelState = viewModel.state
                val numberOfCities = viewModelState.cities.size

                if(locationSelectorState.isScrolling()) {
                    focusManager.clearFocus()
                }

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
        }
    )
}
