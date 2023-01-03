package com.iresetic.weatherreport.locationselection.domain.usecases

import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import javax.inject.Inject

class GetCityData @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    suspend operator fun invoke(cityId: String) = citiesRepository.getCityData(cityId)
}
