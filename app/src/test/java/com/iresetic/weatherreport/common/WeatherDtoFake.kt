package com.iresetic.weatherreport.common

import com.iresetic.weatherreport.weatherforcast.data.api.model.*
import com.iresetic.weatherreport.weatherforcast.domain.model.WeatherData


const val TEMPERATURE_UNIT = "C"
const val HUMIDITY_UNIT = "%"
const val WIND_SPEED_UNIT = "m/s"

val testDetails = Details(
    airTemperature = 1.0,
    relativeHumidity = 24.0,
    windSpeed = 100.0
)

val testInstant = Instant(
    details = testDetails
)

val testData = Data(
    instant = testInstant
)

val testUnits = Units(
    airTemperature = TEMPERATURE_UNIT,
    relativeHumidity = HUMIDITY_UNIT,
    windSpeed = WIND_SPEED_UNIT,
)

val testTimeSeries = TimeSeries(
    `data` = testData
)

val testMeta = Meta(
    units = testUnits
)

val testProperties = Properties(
    meta = testMeta,
    timeSeries = listOf(testTimeSeries)
)

val testWeatherDto = WeatherDto(
    properties = testProperties
)

val testCurrentTemperature = WeatherData(
    value = "1.0",
    measuringUnit = TEMPERATURE_UNIT
)

val testHumidity = WeatherData(
    value = "90.0",
    measuringUnit = HUMIDITY_UNIT
)

val testWindSpeed = WeatherData(
    value = "20",
    measuringUnit = WIND_SPEED_UNIT
)
