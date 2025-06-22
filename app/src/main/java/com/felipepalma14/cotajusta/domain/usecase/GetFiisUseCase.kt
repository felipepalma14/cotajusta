package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.data.model.FiiResponse
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFiisUseCase @Inject constructor(
    private val repository: FiiRepository
) {
    suspend operator fun invoke(): Flow<Result<FiiResponse>> = repository.getFiis()
}
