package com.iresetic.weatherreport.weatherforcast.domain.repositories

import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport

interface WeatherReportRepository {
    suspend fun getCityWeatherReport(city: City): CityWeatherReport

    suspend fun cacheCityWeatherReport(cityWeatherReport: CityWeatherReport)

    suspend fun getCityWeatherReportFromCache(city: City): CityWeatherReport
}