package com.iresetic.weatherreport.locationselection.domain.usecases

import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import javax.inject.Inject

class GetAllCities @Inject constructor(
    private val citiesRepository: CitiesRepository
) {
    suspend operator fun invoke() = citiesRepository.getAllCities()
}
