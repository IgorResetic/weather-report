package com.iresetic.weatherreport.core.domain.usecases

import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.util.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveSelectCity @Inject constructor(
    private val localDataSource: SimpleLocalDataSource,
    private val dispatchersProvider: DispatchersProvider
){
    suspend operator fun invoke(city: City) {
        withContext(dispatchersProvider.io()) {
            localDataSource.setSelectedCity(city)
        }
    }
}