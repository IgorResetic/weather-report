package com.iresetic.weatherreport.locationselection.presentation

import com.iresetic.weatherreport.locationselection.presentation.model.UICity

sealed class LocationSelectorEvent {
    object GetCities: LocationSelectorEvent()
    data class SelectCity(val city: UICity): LocationSelectorEvent();
}
