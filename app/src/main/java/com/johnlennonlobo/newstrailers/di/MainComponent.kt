package com.johnlennonlobo.newstrailers.di

import com.johnlennonlobo.newstrailers.MainActivity
import com.johnlennonlobo.newstrailers.ui.HomeFragment
import dagger.Subcomponent

@Subcomponent(modules = [])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory{
        fun create():MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
}