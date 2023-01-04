package com.iresetic.weatherreport.locationselection.presentation

import androidx.compose.ui.graphics.Color
import com.iresetic.weatherreport.locationselection.presentation.model.UICity

data class LocationSelectorUiState(
    val isLoading: Boolean = false,
    val cities: List<UICity> = emptyList(),
    val selectedCity: UICity? = null,
    val searchedCityValue: String = ""
)
