package com.iresetic.weatherreport.locationselection.presentation

import com.iresetic.weatherreport.locationselection.presentation.model.UICity

sealed class LocationSelectorEvent {
    object GetCities: LocationSelectorEvent()
    object ClearSearchBarText: LocationSelectorEvent()
    data class SelectCity(val city: UICity): LocationSelectorEvent()
    data class SearchForCity(val searchValue: String): LocationSelectorEvent()
}
