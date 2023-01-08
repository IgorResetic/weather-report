package com.iresetic.weatherreport.core.domain.usecases

import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import com.iresetic.weatherreport.core.domain.model.city.City
import javax.inject.Inject

class SaveSelectCity @Inject constructor(
    private val localDataSource: SimpleLocalDataSource
){
    suspend operator fun invoke(city: City) = localDataSource.setSelectedCity(city)
}