package com.iresetic.weatherreport.weatherforcast.data.repositories

import android.content.Context
import com.iresetic.weatherreport.R
import com.iresetic.weatherreport.core.constants.USER_AGENT
import com.iresetic.weatherreport.core.domain.model.city.City
import com.iresetic.weatherreport.core.util.Resource
import com.iresetic.weatherreport.weatherforcast.data.api.WeatherReportApi
import com.iresetic.weatherreport.weatherforcast.data.api.model.Details
import com.iresetic.weatherreport.weatherforcast.data.api.model.Units
import com.iresetic.weatherreport.weatherforcast.data.api.model.WeatherDto
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.model.WeatherData
import com.iresetic.weatherreport.weatherforcast.domain.repositories.WeatherReportRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class WeatherReportRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val weatherReportApi: WeatherReportApi,
): WeatherReportRepository {
    private lateinit var measuringUnits: Units
    private lateinit var weatherDto: WeatherDto
    private var details: Details? = null

    override suspend fun getCityWeatherReport(city: City): Resource<CityWeatherReport> {
        try {
            weatherDto =  weatherReportApi.getWeatherReportData(
                header = USER_AGENT,
                lat = city.latitude.toDouble(),
                lon = city.longitude.toDouble()
            )

            getMeasuringUnits()
            getWeatherReportDetails()

            return Resource.Success(
                createCityWeatherReport(city.name, city.id)
            )
        } catch (e: IOException) {
            return Resource.Error(
                message = context.getString(R.string.weather_forecast_io_error)
            )
        } catch (e: HttpException) {
            return Resource.Error(
                message = context.getString(R.string.weather_forecast_api_error)
            )
        }
    }

    private fun getMeasuringUnits() {
        measuringUnits = weatherDto.properties?.meta?.units ?: Units(
            airTemperature = "",
            relativeHumidity = "",
            windSpeed = ""
        )
    }

    private fun getWeatherReportDetails() {
        details = weatherDto.properties?.timeSeries?.first()?.data?.instant?.details

        if(details == null) throw IOException()
    }

    private fun createCityWeatherReport(cityName: String, id: String): CityWeatherReport = CityWeatherReport(
        id = id,
        cityName = cityName,
        currentTemperature = getCurrentTemperature(),
        humidity = getHumidity(),
        windSpeed = getWindSpeed()
    )

    private fun getCurrentTemperature(): WeatherData = WeatherData(
        value = details?.let { it.airTemperature.toString() } ?: "",
        measuringUnit = measuringUnits.airTemperature ?: ""
    )

    private fun getHumidity(): WeatherData = WeatherData(
        value = details?.let { it.relativeHumidity.toString() } ?: "",
        measuringUnit = measuringUnits.relativeHumidity ?: ""
    )

    private fun getWindSpeed(): WeatherData = WeatherData(
        value = details?.let { it.windSpeed.toString() } ?: "",
        measuringUnit = measuringUnits.windSpeed ?: ""
    )
}