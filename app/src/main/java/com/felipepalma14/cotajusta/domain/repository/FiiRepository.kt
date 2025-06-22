package com.felipepalma14.cotajusta.domain.repository

import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.data.model.FiiResponse
import kotlinx.coroutines.flow.Flow

interface FiiRepository {
    suspend fun getFiis(): Flow<Result<FiiResponse>>
    suspend fun getLocalFiis(): Flow<List<FiiEntity>>
    suspend fun insertFiis(fiis: List<FiiEntity>)
    suspend fun setFavorite(code: String, isFavorite: Boolean)
    suspend fun getFavoriteFiis(): Flow<List<FiiEntity>>
}
