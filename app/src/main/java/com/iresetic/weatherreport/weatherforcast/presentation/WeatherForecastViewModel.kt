package com.iresetic.weatherreport.weatherforcast.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iresetic.weatherreport.core.domain.usecases.GetSavedCity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherForecastViewModel @Inject constructor(
    private val getSavedCity: GetSavedCity
): ViewModel() {
}