package com.iresetic.weatherreport.core.data.local.datasource

import com.iresetic.weatherreport.core.domain.model.city.City

/** Persistent local data source for saving some basic simple values. */
interface SimpleLocalDataSource {

    /** Returns currently selected city. */
    suspend fun getSelectedCity(): City?

    /** Saves currently selected city. */
    suspend fun setSelectedCity(city: City)
}
