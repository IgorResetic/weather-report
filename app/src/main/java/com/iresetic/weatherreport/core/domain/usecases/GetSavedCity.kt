package com.iresetic.weatherreport.core.domain.usecases

import com.iresetic.weatherreport.core.data.local.datasource.SimpleLocalDataSource
import javax.inject.Inject

class GetSavedCity @Inject constructor(
    private val localDataSource: SimpleLocalDataSource
){
  suspend operator fun invoke() = localDataSource.getSelectedCity()
}
