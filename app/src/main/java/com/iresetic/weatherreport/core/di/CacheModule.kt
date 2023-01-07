package com.iresetic.weatherreport.core.di

import android.content.Context
import androidx.room.Room
import com.iresetic.weatherreport.core.data.cache.WeatherForecastDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context
    ): WeatherForecastDatabase {
        return Room.databaseBuilder(
            context,
            WeatherForecastDatabase::class.java,
            WeatherForecastDatabase.DATABASE_NAME
        )
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherReportDao(weatherForecastDatabase: WeatherForecastDatabase) =
        weatherForecastDatabase.weatherReportDao()
}