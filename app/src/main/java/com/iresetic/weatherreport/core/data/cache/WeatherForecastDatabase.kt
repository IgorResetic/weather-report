package com.iresetic.weatherreport.core.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iresetic.weatherreport.weatherforcast.data.cache.daos.WeatherReportDao
import com.iresetic.weatherreport.weatherforcast.data.cache.model.CityWeatherReportEntity

@Database(entities = [CityWeatherReportEntity::class], version = 1)
abstract class WeatherForecastDatabase: RoomDatabase() {
    abstract fun weatherReportDao(): WeatherReportDao

    companion object {
        const val DATABASE_NAME = "weather_forecast.db"
    }
}
