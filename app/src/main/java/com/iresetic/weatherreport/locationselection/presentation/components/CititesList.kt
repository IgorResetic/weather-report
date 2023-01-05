package com.iresetic.weatherreport.locationselection.presentation.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.core.presentation.components.ErrorMessage
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorState
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorViewModel
import com.iresetic.weatherreport.ui.theme.BlackTransparent_15

@Composable
fun CitiesList(locationSelectorState: LocationSelectorState, topPadding: Dp) {
    val viewModel = hiltViewModel<LocationSelectorViewModel>()
    val focusManager = LocalFocusManager.current

    val viewModelState = viewModel.state

    LazyColumn(
        state = locationSelectorState.listState,
        modifier = Modifier.padding(top = topPadding),
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
    }
}