package com.iresetic.weatherreport.core.data.local.datasource

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.iresetic.weatherreport.core.domain.model.city.City
import com.squareup.moshi.Moshi
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/** Specific context extension for accessing our preferences data store. */
val Context.simpleDataStore by preferencesDataStore(name = "simple")

class PreferenceSimpleLocalDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    val moshi: Moshi
): SimpleLocalDataSource {
    private val dataStore = context.simpleDataStore
    private val selectedCityKey = stringPreferencesKey("selectedCity")

    override suspend fun getSelectedCity(): City? = dataStore.data.map {  prefs ->
            val json = prefs[selectedCityKey]
            if(json != null ) {
                @Suppress("BlockingMethodInNonBlockingContext")
                moshi.adapter(City::class.java).fromJson(json)
            } else {
                null
            }
        }.first()

    override suspend fun setSelectedCity(city: City) {
        dataStore.edit { selectedCity ->
            selectedCity[selectedCityKey] = moshi.adapter(City::class.java).toJson(city)
        }
    }
}
