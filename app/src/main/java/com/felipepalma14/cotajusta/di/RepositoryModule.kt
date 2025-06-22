package com.felipepalma14.cotajusta.di

import com.felipepalma14.cotajusta.data.repository.FiiRepositoryImpl
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFiiRepository(
        fiiRepositoryImpl: FiiRepositoryImpl
    ): FiiRepository
}
