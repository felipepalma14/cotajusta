package com.felipepalma14.cotajusta.data.repository

import com.felipepalma14.cotajusta.data.model.FiiResponse
import com.felipepalma14.cotajusta.data.remote.api.FiiApi
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.single
import javax.inject.Inject

class FiiRepositoryImpl @Inject constructor(
    private val api: FiiApi,
    private val fiiDao: FiiDao
) : FiiRepository {
    override suspend fun getFiis(): Flow<Result<FiiResponse>> = flow {
        try {
            val response = api.getFiis()
            emit(Result.success(response.single()))
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override suspend fun getLocalFiis(): Flow<List<FiiEntity>> = fiiDao.getAllFiis()

    override suspend fun insertFiis(fiis: List<FiiEntity>) {
        fiiDao.insertAll(fiis)
    }

    override suspend fun setFavorite(code: String, isFavorite: Boolean) {
        fiiDao.setFavorite(code, isFavorite)
    }

    override suspend fun getFavoriteFiis(): Flow<List<FiiEntity>> = fiiDao.getFavorites()
}
