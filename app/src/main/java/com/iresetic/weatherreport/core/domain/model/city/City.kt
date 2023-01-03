package com.iresetic.weatherreport.core.domain.model.city

import com.iresetic.weatherreport.core.data.local.model.CityDto

data class City(
    val id: String,
    val name: String,
    val geoLocation: GeoLocation,
)
