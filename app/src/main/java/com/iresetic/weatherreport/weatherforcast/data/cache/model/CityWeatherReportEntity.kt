package com.iresetic.weatherreport.weatherforcast.data.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iresetic.weatherreport.weatherforcast.domain.model.CityWeatherReport
import com.iresetic.weatherreport.weatherforcast.domain.model.WeatherData

@Entity(tableName = "city_weather_report")
data class CityWeatherReportEntity(
    @PrimaryKey(autoGenerate = false)
    val cityId: String,
    val cityName: String,
    val currentTemperature: String,
    val temperatureMeasuringUnit: String,
    val humidity: String,
    val humidityMeasuringUnit: String,
    val windSpeed: String,
    val windSpeedMeasuringUnit: String
) {

    fun toDomain() : CityWeatherReport = CityWeatherReport(
        id = cityId,
        cityName = cityName,
        currentTemperature = WeatherData(
            value = currentTemperature,
            measuringUnit = temperatureMeasuringUnit
        ),
        humidity = WeatherData(
            value = humidity,
            measuringUnit = humidityMeasuringUnit
        ),
        windSpeed = WeatherData(
            value = windSpeed,
            measuringUnit = windSpeedMeasuringUnit
        )
    )

    companion object {
        fun fromDomain(cityWeatherReport: CityWeatherReport): CityWeatherReportEntity =
            CityWeatherReportEntity(
                cityId = cityWeatherReport.id,
                cityName = cityWeatherReport.cityName,
                currentTemperature = cityWeatherReport.currentTemperature.value,
                temperatureMeasuringUnit = cityWeatherReport.currentTemperature.measuringUnit,
                humidity = cityWeatherReport.humidity.value,
                humidityMeasuringUnit = cityWeatherReport.humidity.measuringUnit,
                windSpeed = cityWeatherReport.windSpeed.value,
                windSpeedMeasuringUnit = cityWeatherReport.windSpeed.measuringUnit
        )
    }
}
