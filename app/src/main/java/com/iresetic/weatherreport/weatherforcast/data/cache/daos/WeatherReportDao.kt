package com.iresetic.weatherreport.weatherforcast.data.cache.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.iresetic.weatherreport.weatherforcast.data.cache.model.CityWeatherReportEntity

@Dao
interface WeatherReportDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(cityWeatherReport: CityWeatherReportEntity)

    @Query("SELECT * FROM city_weather_report WHERE cityId = :cityId")
    suspend fun getCityWeatherReport(cityId: String): CityWeatherReportEntity?
}
