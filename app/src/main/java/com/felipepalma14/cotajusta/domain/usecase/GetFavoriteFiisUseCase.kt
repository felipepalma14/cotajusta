package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavoriteFiisUseCase @Inject constructor(
    private val repository: FiiRepository
) {
    suspend operator fun invoke(): Flow<List<FiiEntity>> = repository.getFavoriteFiis()
}
