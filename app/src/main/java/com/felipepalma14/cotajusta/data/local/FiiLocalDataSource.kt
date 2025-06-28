package com.felipepalma14.cotajusta.data.local

import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface FiiLocalDataSource {
    suspend fun insert(fii: FiiEntity)
    suspend fun insertAll(fiis: List<FiiEntity>)
    suspend fun setFavorite(code: String, isFavorite: Boolean)
    suspend fun getFavorites(): Flow<List<FiiEntity>>
    fun getAllFiis(): Flow<List<FiiEntity>>
}

class FiiLocalDataSourceImpl @Inject constructor(
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val fiiDao: FiiDao
) : FiiLocalDataSource {

    override suspend fun insert(fii: FiiEntity) {
        withContext(coroutineDispatcher) {
            fiiDao.insertAll(listOf(fii))
        }
    }

    override suspend fun insertAll(fiis: List<FiiEntity>) {
        withContext(coroutineDispatcher) {
            fiiDao.insertAll(fiis)
        }
    }

    override suspend fun setFavorite(code: String, isFavorite: Boolean) {
        withContext(coroutineDispatcher) {
            fiiDao.setFavorite(code, isFavorite)
        }
    }

    override suspend fun getFavorites(): Flow<List<FiiEntity>> {
        return fiiDao.getFavorites()
    }

    override fun getAllFiis(): Flow<List<FiiEntity>> {
        return fiiDao.getAllFiis()
    }
}