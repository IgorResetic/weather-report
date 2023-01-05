package com.iresetic.weatherreport.core.data.local.model

import com.iresetic.weatherreport.core.domain.model.city.City

/** Persistent local data source for saving some basic simple values. */
interface SimpleLocalDataSource {

    /** Returns currently selected city. */
    suspend fun getSelectedCity(): String

    /** Saves currently selected city. */
    suspend fun setSelectedCity(cityId: String)
}
