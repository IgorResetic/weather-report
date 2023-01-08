package com.iresetic.weatherreport.locationselection.domain.usecases

import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.util.DispatchersProvider
import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetCityData @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val dispatchersProvider: DispatchersProvider

) {
    suspend operator fun invoke(cityId: String): City? {
        return withContext(dispatchersProvider.io()) {
            citiesRepository.getCityData(cityId)
        }
    }
}
