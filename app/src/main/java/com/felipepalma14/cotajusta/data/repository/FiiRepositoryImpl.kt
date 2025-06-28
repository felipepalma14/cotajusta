package com.felipepalma14.cotajusta.data.repository

import com.felipepalma14.cotajusta.data.local.FiiLocalDataSource
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.data.remote.model.FiiResponse
import com.felipepalma14.cotajusta.data.remote.FiiRemoteDataSource
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

class FiiRepositoryImpl @Inject constructor(
    private val remote: FiiRemoteDataSource,
    private val local: FiiLocalDataSource
) : FiiRepository {
    override suspend fun getFiis(): Flow<Result<FiiResponse>> =
        remote.fetchFii()
            .catch { emit(Result.failure(it)) }

    override suspend fun getLocalFiis(): Flow<List<FiiEntity>> = local.getAllFiis()

    override suspend fun insertFiis(fiis: List<FiiEntity>) {
        local.insertAll(fiis)
    }

    override suspend fun setFavorite(code: String, isFavorite: Boolean) {
        local.setFavorite(code, isFavorite)
    }

    override suspend fun getFavoriteFiis(): Flow<List<FiiEntity>> = local.getFavorites()
}
