package com.iresetic.weatherreport.locationselection.presentation.model

import com.iresetic.weatherreport.core.domain.model.city.City

data class UICity (
    val cityId: String,
    val cityName: String
) {
    companion object {
        fun fromDomain(city: City): UICity = UICity(
            cityId = city.id,
            cityName = city.name
        )
    }
}
