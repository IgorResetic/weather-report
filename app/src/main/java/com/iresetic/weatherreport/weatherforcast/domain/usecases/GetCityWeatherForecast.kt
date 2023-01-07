package com.iresetic.weatherreport.weatherforcast.domain.usecases

import android.content.Context
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.util.Resource
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.repositories.WeatherReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCityWeatherForecast @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherForecastRepository: WeatherReportRepository
) {
    private lateinit var selectedCity: City

    suspend operator fun invoke(city: City): Resource<CityWeatherReport> {
        selectedCity = city
        return withContext(Dispatchers.IO) {
            try {
                val report = weatherForecastRepository.getCityWeatherReport(city)

                weatherForecastRepository.cacheCityWeatherReport(report)

                return@withContext Resource.Success(
                    report
                )
            }  catch (e: Exception) {
                return@withContext handleExceptions(e)
            }
        }
    }

    private suspend fun handleExceptions(e: Exception): Resource<CityWeatherReport>{
        val cachedReport = getWeatherReportFromCache()

        return when(e) {
            is IOException -> Resource.Error(
                message = context.getString(R.string.weather_forecast_io_error),
                data = cachedReport
            )

            is HttpException -> Resource.Error(
                message = context.getString(R.string.weather_forecast_api_error),
                data = cachedReport
            )
            else -> Resource.Error(
                message = context.getString(R.string.weather_forecast_general_error)
            )
        }
    }

    private suspend fun getWeatherReportFromCache(): CityWeatherReport =
        weatherForecastRepository.getCityWeatherReportFromCache(selectedCity)
}