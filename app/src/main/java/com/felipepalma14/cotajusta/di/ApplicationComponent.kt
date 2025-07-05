package com.felipepalma14.cotajusta.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DispatcherModule::class,
        NetworkModule::class,
        DataBaseModule::class,
        DataSourceModule::class,
        RepositoryModule::class,
        UseCaseModule::class,
    ]
)
interface ApplicationComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}