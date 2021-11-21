package com.johnlennonlobo.newstrailers.di

import com.johnlennonlobo.newstrailers.repository.HomeDataSource
import com.johnlennonlobo.newstrailers.repository.HomeDataSourceImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Singleton
    @Binds
    abstract fun provideHomeDataSource(dataSourceImpl: HomeDataSourceImpl) : HomeDataSource
}