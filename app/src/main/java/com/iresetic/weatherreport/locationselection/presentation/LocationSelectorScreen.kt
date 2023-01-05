package com.iresetic.weatherreport.locationselection.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.core.presentation.components.ErrorMessage
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.GetCities
import com.iresetic.weatherreport.locationselection.presentation.components.CitiesList
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

    LaunchedEffect(Unit) {
        this.launch(Dispatchers.IO) {
            viewModel.onEvent(GetCities)
        }
    }

    Scaffold(
        topBar = { SearchTopBar(navController, locationSelectorState) },
        content = {
            val viewModelState = viewModel.state

            when {
                viewModelState.isLoading -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                !viewModelState.isLoading && viewModelState.cities.isEmpty() -> {
                    ErrorMessage(errorMessage = stringResource(
                        id = R.string.location_selector_error_message
                    ))
                }

                else -> {
                    CitiesList(
                        locationSelectorState,
                        it.calculateTopPadding()
                    )
                }
            }

/*            if (!viewModelState.isLoading) {
                CitiesList(
                    locationSelectorState,
                    it.calculateTopPadding()
                )
*//*                LazyColumn(
                    state = locationSelectorState.listState,
                    modifier = Modifier.padding(top = it.calculateTopPadding()),
                ) {
                    val numberOfCities = viewModelState.cities.size

                    if (locationSelectorState.isScrolling()) {
                        focusManager.clearFocus()
                    }
                    items(numberOfCities) { index ->
                        val cityName = viewModelState.cities[index].cityName
                        CityListItem(cityName = cityName)
                        Divider(color = BlackTransparent_15, thickness = 0.8.dp)
                    }

                    item {
                        if (viewModel.cities.isEmpty()) {
                            ErrorMessage(errorMessage = stringResource(
                                id = R.string.location_selector_error_message
                            ))
                        }
                    }
                }*//*
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }*/

        }
    )
}
