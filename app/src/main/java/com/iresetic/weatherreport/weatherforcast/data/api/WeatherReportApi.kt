package com.iresetic.weatherreport.weatherforcast.data.api

import com.iresetic.weatherreport.weatherforcast.data.api.model.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherReportApi {

    @GET("compact/")
    suspend fun getWeatherReportData(
        @Header("User-Agent") header: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherDto
}