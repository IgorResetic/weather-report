package com.iresetic.weatherreport.core.di

import com.iresetic.weatherreport.core.util.CoroutineDispatchersProvider
import com.iresetic.weatherreport.core.util.DispatchersProvider
import com.iresetic.weatherreport.locationselection.data.CitiesRepositoryImpl
import com.iresetic.weatherreport.locationselection.domain.repositories.CitiesRepository
import com.iresetic.weatherreport.weatherforcast.data.repositories.WeatherReportRepositoryImpl
import com.iresetic.weatherreport.weatherforcast.domain.repositories.WeatherReportRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCitiesRepository(
        citiesRepositoryImpl: CitiesRepositoryImpl
    ): CitiesRepository

    @Binds
    @Singleton
    abstract fun bindWeatherReportRepository(
        weatherReportRepositoryImpl: WeatherReportRepositoryImpl
    ): WeatherReportRepository

    @Binds
    abstract fun bindDispatchersProvider(dispatchersProvider: CoroutineDispatchersProvider):
        DispatchersProvider
}
