package com.johnlennonlobo.newstrailers.di

import androidx.lifecycle.ViewModel
import com.johnlennonlobo.newstrailers.ui.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface MainModole {
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel
}