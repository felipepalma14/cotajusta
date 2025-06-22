package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import javax.inject.Inject

class SaveFiisUseCase @Inject constructor(
    private val repository: FiiRepository
) {
    suspend operator fun invoke(fiis: List<FiiEntity>) = repository.insertFiis(fiis)
}
