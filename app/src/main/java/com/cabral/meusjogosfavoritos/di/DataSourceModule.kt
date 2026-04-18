package com.cabral.meusjogosfavoritos.di

import com.cabral.meusjogosfavoritos.data.local.datasource.LocalDataSourceImpl
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
    abstract fun bindLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl,
    ): LocalDataSource
}

