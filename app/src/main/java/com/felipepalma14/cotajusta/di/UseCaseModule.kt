package com.felipepalma14.cotajusta.di

import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import com.felipepalma14.cotajusta.domain.usecase.GetFavoriteFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.GetFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.GetLocalFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.SaveFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.ToggleFavoriteFiiUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetFiisUseCase(repository: FiiRepository): GetFiisUseCase {
        return GetFiisUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetLocalFiisUseCase(repository: FiiRepository): GetLocalFiisUseCase {
        return GetLocalFiisUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSaveFiisUseCase(repository: FiiRepository): SaveFiisUseCase {
        return SaveFiisUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideToggleFavoriteFiiUseCase(repository: FiiRepository): ToggleFavoriteFiiUseCase {
        return ToggleFavoriteFiiUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetFavoriteFiisUseCase(repository: FiiRepository): GetFavoriteFiisUseCase {
        return GetFavoriteFiisUseCase(repository)
    }
}
