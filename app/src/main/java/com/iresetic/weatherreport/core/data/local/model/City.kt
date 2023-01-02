package com.iresetic.weatherreport.core.data.local.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "admin1") val administrationRegion: String,
    @field:Json(name = "lat") val latitude: String,
    @field:Json(name = "lon") val longitude: String,
    @field:Json(name = "pop") val population: String
)
