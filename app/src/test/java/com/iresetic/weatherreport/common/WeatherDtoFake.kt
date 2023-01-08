package com.iresetic.weatherreport.common

import com.iresetic.weatherreport.weatherforcast.data.api.model.*
import com.iresetic.weatherreport.weatherforcast.domain.model.WeatherData


const val TEMPERATURE_UNIT = "C"
const val HUMIDITY_UNIT = "%"
const val WIND_SPEED_UNIT = "m/s"
const val TEST_TEMP_ONE = 1.0
const val TEST_HUMIDITY_ONE = 24.0
const val TEST_WIND_SPEED_ONE = 100.0

val testDetails = Details(
    airTemperature = TEST_TEMP_ONE,
    relativeHumidity = TEST_HUMIDITY_ONE,
    windSpeed = TEST_WIND_SPEED_ONE
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
    value = TEST_TEMP_ONE.toString(),
    measuringUnit = TEMPERATURE_UNIT
)

val testHumidity = WeatherData(
    value = TEST_HUMIDITY_ONE.toString(),
    measuringUnit = HUMIDITY_UNIT
)

val testWindSpeed = WeatherData(
    value = TEST_WIND_SPEED_ONE.toString(),
    measuringUnit = WIND_SPEED_UNIT
)
