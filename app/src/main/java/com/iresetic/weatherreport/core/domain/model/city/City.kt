package com.iresetic.weatherreport.core.domain.model.city

data class City(
    val id: String,
    val name: String,
    val geoLocation: GeoLocation,
)
