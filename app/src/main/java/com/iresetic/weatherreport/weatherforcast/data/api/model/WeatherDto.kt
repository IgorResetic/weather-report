package com.iresetic.weatherreport.weatherforcast.data.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherDto(
    @field:Json(name = "properties") val properties: Properties?,
)

@JsonClass(generateAdapter = true)
data class Properties(
    @field:Json(name = "meta") val meta: Meta?,
    @field:Json(name = "timeseries") val timeSeries: List<TimeSeries>?
)

@JsonClass(generateAdapter = true)
data class Meta(
    @field:Json(name = "units") val units: Units,
)

@JsonClass(generateAdapter = true)
data class TimeSeries(
    @field:Json val `data`: Data?,
)

@JsonClass(generateAdapter = true)
data class Units(
    @field:Json(name = "air_temperature") val airTemperature: String?,
    @field:Json(name = "relative_humidity") val relativeHumidity: String?,
    @field:Json(name = "wind_speed") val windSpeed: String?
)

@JsonClass(generateAdapter = true)
data class Data(
    @field:Json(name = "instant") val instant: Instant?,
)

@JsonClass(generateAdapter = true)
data class Instant(
    @field:Json val details: Details?
)

@JsonClass(generateAdapter = true)
data class Details(
    @field:Json(name = "air_temperature") val airTemperature: Double?,
    @field:Json(name = "relative_humidity") val relativeHumidity: Double?,
    @field:Json(name = "wind_speed") val windSpeed: Double?
)
