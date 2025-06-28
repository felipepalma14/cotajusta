package com.felipepalma14.cotajusta.di

import com.felipepalma14.cotajusta.data.local.FiiLocalDataSource
import com.felipepalma14.cotajusta.data.local.FiiLocalDataSourceImpl
import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import com.felipepalma14.cotajusta.data.remote.FiiRemoteDataSource
import com.felipepalma14.cotajusta.data.remote.api.FiiApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideFiiLocalDataSource(
        fiiDao: FiiDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): FiiLocalDataSource = FiiLocalDataSourceImpl(ioDispatcher, fiiDao)

    @Provides
    @Singleton
    fun provideFiiRemoteDataSource(
        fiiApi: FiiApi
    ): FiiRemoteDataSource = FiiRemoteDataSource(fiiApi)
}