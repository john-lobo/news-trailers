package com.johnlennonlobo.newstrailers.di

import android.content.Context
import com.johnlennonlobo.newstrailers.database.DispatcherModule
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton // nao tem mais de uma instancia
@Component(modules = [
    MainModule::class,
    NetworkModule::class,
    ViewModelBuilderModule::class,
    SubComponentModule::class,
    DataModule::class,
    DispatcherModule::class
])
interface ApplicationComponent {

    @Component.Factory
    interface Factory{
        fun create(@BindsInstance applicationContext: Context):ApplicationComponent
    }

    fun mainComponent(): MainComponent.Factory
}

@Module(subcomponents = [MainComponent::class])
object SubComponentModule