package com.iresetic.weatherreport.core.data.local.model

import com.iresetic.weatherreport.core.domain.model.city.City
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityDto(
    @field:Json(name = "id") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "country") val country: String,
    @field:Json(name = "admin1") val administrationRegion: String? = "",
    @field:Json(name = "lat") val latitude: String,
    @field:Json(name = "lon") val longitude: String,
    @field:Json(name = "pop") val population: String
) {

    fun toDomain(): City = City(
        id = id,
        name = name,
        latitude = latitude,
        longitude = longitude
    )
}
