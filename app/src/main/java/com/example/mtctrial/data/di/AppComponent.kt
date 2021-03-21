package com.example.mtctrial.data.di

import com.example.mtctrial.ui.viewmodel.FavoritesViewModel
import com.example.mtctrial.ui.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Component(modules = [DaggerModule::class])
@Singleton
interface AppComponent {
    fun inject(target: MainViewModel)
    fun inject(target: FavoritesViewModel)
}