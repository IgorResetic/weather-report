package com.iresetic.weatherreport.weatherforcast.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.domain.usecases.GetSavedCity
import com.iresetic.weatherreport.core.util.Resource
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.repositories.WeatherReportRepository
import com.iresetic.weatherreport.weatherforcast.presentation.WeatherForecastEvent.*
import com.iresetic.weatherreport.weatherforcast.presentation.model.UiCityWeatherReport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val getSavedCity: GetSavedCity,
    private val weatherReportRepository: WeatherReportRepository
): ViewModel() {
    var state by mutableStateOf(WeatherForecastUiState())
        private set

    fun onEvent(event: WeatherForecastEvent) {
        when(event) {
            is GetCityWeatherReport -> {
                state = state.copy(
                    isLoading = true,
                    isRefreshing = false,
                    error = null
                )

                getCityWeatherForecast()
            }
            is RefreshWeatherForecastScreen -> {
               state = state.copy(
                   isLoading = true,
                   isRefreshing = true
               )

               getCityWeatherForecast()
            }
        }
    }

    fun getCityWeatherForecast() {
        viewModelScope.launch {
            val city = getSavedCity.invoke() ?: City.emptyCityModel()
            if(city != City.emptyCityModel()) {
                when(val cityWeatherReport = weatherReportRepository.getCityWeatherReport(city)) {
                    is Resource.Success -> {
                        state = state.copy(
                            isLoading = false,
                            weatherReport = UiCityWeatherReport.fromDomain(
                                cityWeatherReport.data ?:
                                CityWeatherReport.emptyCityWeatherReport()
                            ),
                            isCitySelected = true,
                            isRefreshing = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            isLoading = false,
                            isCitySelected = true,
                            isRefreshing = false,
                            error = cityWeatherReport.message
                        )
                    }
                }
            } else {
                state = state.copy(
                    isLoading = false,
                    isCitySelected = false,
                    isRefreshing = false,
                    error = null
                )
            }
        }
    }
}
