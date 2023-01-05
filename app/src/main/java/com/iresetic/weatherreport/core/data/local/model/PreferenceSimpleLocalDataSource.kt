package com.iresetic.weatherreport.core.data.local.model

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iresetic.weatherreport.core.domain.model.city.City
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** Specific context extension for accessing our preferences data store. */
val Context.simpleDataStore by preferencesDataStore(name = "simple")

class PreferenceSimpleLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
): SimpleLocalDataSource {
    private val dataStore = context.simpleDataStore
    private val selectedCityKey = stringPreferencesKey("selectedCity")

    override suspend fun getSelectedCity(): String {
        return dataStore.data.map { it[selectedCityKey] }.first() ?: ""
    }

    override suspend fun setSelectedCity(cityId: String) {
        dataStore.edit { selectedCity ->
            selectedCity[selectedCityKey] = cityId

        }
    }
}
