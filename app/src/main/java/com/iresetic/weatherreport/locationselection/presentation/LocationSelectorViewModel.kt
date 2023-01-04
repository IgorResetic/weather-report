package com.iresetic.weatherreport.locationselection.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iresetic.weatherreport.locationselection.domain.usecases.GetAllCities
import com.iresetic.weatherreport.locationselection.domain.usecases.GetCityData
import com.iresetic.weatherreport.locationselection.presentation.LocationSelectorEvent.*
import com.iresetic.weatherreport.locationselection.presentation.model.UICity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSelectorViewModel @Inject constructor(
    private val getAllCities: GetAllCities,
    private val getCityData: GetCityData
) : ViewModel() {
    var state by mutableStateOf(LocationSelectorUiState())
        private set
    var cities = emptyList<UICity>()

    fun onEvent(event: LocationSelectorEvent) {
        when(event) {
            is GetCities -> {
                state = state.copy( isLoading = true )
                loadCities()
            }
            is SelectCity -> {
                selectCity(event.city.cityId)
            }

            is SearchForCity -> {
                filterCities(event.searchValue)
            }
            is ClearSearchBarText -> {
                clearSearchFilter()
            }
        }
    }

    private fun loadCities() {
        viewModelScope.launch {
            cities = getAllCities.invoke()
                .map { UICity.fromDomain(it) }
                .sortedBy { it.cityName }

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

    private fun filterCities(searchValue: String) {
        val filterCityList = if(searchValue.isEmpty()) {
            cities
        } else {
            cities.filter {
                it.cityName.contains(searchValue, ignoreCase = true)
            }
        }

        state = state.copy(
            searchedCityValue = searchValue,
            cities = filterCityList
        )
    }

    private fun clearSearchFilter() {
        state = state.copy(
            searchedCityValue = "",
            cities = cities
        )
    }
}
