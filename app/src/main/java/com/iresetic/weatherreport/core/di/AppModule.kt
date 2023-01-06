package com.iresetic.weatherreport.core.di

import com.iresetic.weatherreport.core.constants.YR_BASE_URL
import com.iresetic.weatherreport.weatherforcast.data.api.WeatherReportApi
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideMoshiBuilder(): Moshi = Moshi.Builder().build()

    @Provides
    fun provideRetrofit(): Retrofit.Builder = Retrofit.Builder()
        .baseUrl(YR_BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())

    @Singleton
    @Provides
    fun provideWeatherReportApi(builder: Retrofit.Builder): WeatherReportApi =
        builder.build().create(WeatherReportApi::class.java)
}