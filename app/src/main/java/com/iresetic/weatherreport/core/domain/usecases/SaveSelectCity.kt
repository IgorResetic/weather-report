package com.iresetic.weatherreport.core.domain.usecases

import com.iresetic.weatherreport.core.data.local.model.SimpleLocalDataSource
import javax.inject.Inject

class SaveSelectCity @Inject constructor(
    private val localDataSource: SimpleLocalDataSource
){
    suspend operator fun invoke(cityId: String) = localDataSource.setSelectedCity(cityId)
}