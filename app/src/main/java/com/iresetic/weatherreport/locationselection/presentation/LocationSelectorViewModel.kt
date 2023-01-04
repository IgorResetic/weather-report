package com.iresetic.weatherreport.locationselection.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iresetic.weatherreport.locationselection.domain.usecases.GetAllCities
import com.iresetic.weatherreport.locationselection.domain.usecases.GetCityData
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.GetCities
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.SelectCity
import com.iresetic.weatherreport.locationselection.presentation.model.UICity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LocationSelectorViewModel @Inject constructor(
    private val getAllCities: GetAllCities,
    private val getCityData: GetCityData
) : ViewModel() {
    var state by mutableStateOf(LocationSelectorUiState())
        private set

    fun onEvent(event: LocationSelectorEvent) {
        when(event) {
            is GetCities -> {
                state = state.copy( isLoading = true )
                loadCities()
            }
            is SelectCity -> {
                selectCity(event.city.cityId)
            }
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            val cities = getAllCities.invoke().map { UICity.fromDomain(it) }
            state = state.copy(
                isLoading = false,
                cities = cities
            )
        }
    }

    private fun selectCity(cityId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getCityData.invoke(cityId)
        }
    }
}
