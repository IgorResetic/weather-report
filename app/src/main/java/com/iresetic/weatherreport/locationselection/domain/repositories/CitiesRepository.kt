package com.iresetic.weatherreport.locationselection.domain.repositories

import com.iresetic.weatherreport.core.domain.model.city.City

interface CitiesRepository {
    suspend fun getAllCities(): List<City>
    suspend fun getCityData(cityId: String): City?
}
