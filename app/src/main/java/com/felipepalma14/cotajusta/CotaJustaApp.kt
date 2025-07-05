package com.felipepalma14.cotajusta

import android.app.Application
import com.felipepalma14.cotajusta.di.ApplicationComponent
import com.felipepalma14.cotajusta.di.DaggerApplicationComponent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CotaJustaApp : Application() {
    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.factory().create(this)
    }
}
