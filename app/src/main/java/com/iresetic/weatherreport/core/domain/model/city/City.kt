package com.iresetic.weatherreport.core.domain.model.city

import com.iresetic.weatherreport.core.data.local.model.CityDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class City(
    val id: String,
    val name: String,
    val latitude: String,
    val longitude: String,
) {
    companion object {
        fun emptyCityModel() = City(
            id = "",
            name = "",
            latitude = "",
            longitude = ""
        )
    }
}
