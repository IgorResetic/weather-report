package com.iresetic.weatherreport.core.di

import com.iresetic.weatherreport.core.data.local.model.PreferenceSimpleLocalDataSource
import com.iresetic.weatherreport.core.data.local.model.SimpleLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindSimpleLocalDataSource(
        preferenceSimpleLocalDataSource: PreferenceSimpleLocalDataSource
    ): SimpleLocalDataSource
}
