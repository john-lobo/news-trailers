package com.johnlennonlobo.newstrailers.database

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Module
object DispatcherModule {

    @DefaultDispatcher
    @Provides
    fun providersDefaultDispachers() = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providersIoDispachers() = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providerMainDispachers() = Dispatchers.Main

}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher