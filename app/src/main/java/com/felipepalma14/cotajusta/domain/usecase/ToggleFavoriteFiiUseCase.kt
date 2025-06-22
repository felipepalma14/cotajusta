package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import javax.inject.Inject

class ToggleFavoriteFiiUseCase @Inject constructor(
    private val repository: FiiRepository
) {
    suspend operator fun invoke(code: String, isFavorite: Boolean) = repository.setFavorite(code, isFavorite)
}
