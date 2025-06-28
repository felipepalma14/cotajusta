package com.felipepalma14.cotajusta.di

import android.content.Context
import androidx.room.Room
import com.felipepalma14.cotajusta.data.local.AppDatabase
import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideDao(database: AppDatabase): FiiDao {
        return  database.fiiDao()
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context = context,
            AppDatabase::class.java,
            "fruit_database"
        )
            .fallbackToDestructiveMigration(false)
            .build()
    }
}
