package com.example.mtctrial

import android.app.Application
import com.example.mtctrial.data.di.AppComponent
import com.example.mtctrial.data.di.DaggerAppComponent
import com.example.mtctrial.data.di.DaggerModule

class TrialApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = initDagger(this)
    }

    private fun initDagger(app: TrialApplication): AppComponent =
        DaggerAppComponent.builder()
            .daggerModule(DaggerModule(app))
            .build()
}