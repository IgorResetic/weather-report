package com.iresetic.weatherreport.locationselection.data

import android.content.Context
import android.util.Log
import com.iresetic.weatherreport.core.constants.LOCATIONS_JSON_FILENAME
import com.iresetic.weatherreport.core.data.local.model.CityDto
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.IOException
import javax.inject.Inject

class CitiesRepositoryImpl@Inject constructor(
    @ApplicationContext private val context: Context,
    private val moshi: Moshi,
) : CitiesRepository {
    private var cities = mapOf<String, City>()

    override suspend fun getAllCities(): List<City> {
        populateCitiesMap()

        return cities.values.toList()
    }

    override suspend fun getCityData(cityId: String): City? {
        populateCitiesMap()

        return cities[cityId]
    }

    private fun populateCitiesMap() {
        if(cities.isEmpty()) {
            Timber.tag("CitiesRepository").i("Populate cities Map")
            Log.d("TEST_CITY", "get Cities")
            cities = try {
                val locationsJson = getCitiesJson()
                val cityAdapter = getCitiesMoshiAdapter()

                val listOfCitiesFromJson = cityAdapter.fromJson(locationsJson) ?: emptyList()
                listOfCitiesFromJson.associateBy({it.id}, {it.toDomain()})
            } catch (e: IOException) {
                Timber.tag("CitiesRepository").e("Error when populating the cities: $e")
                emptyMap()
            }
        }
    }

    private fun getCitiesJson(): String =
        context.assets.open(LOCATIONS_JSON_FILENAME)
        .bufferedReader()
        .use { it.readText() }

    private fun getCitiesMoshiAdapter(): JsonAdapter<List<CityDto>> {
        val listType = Types.newParameterizedType(List::class.java, CityDto::class.java)
        return moshi.adapter(listType)
    }
}
