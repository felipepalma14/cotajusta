package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ToggleFavoriteFiiUseCaseTest {
    private lateinit var repository: FiiRepository
    private lateinit var useCase: ToggleFavoriteFiiUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = ToggleFavoriteFiiUseCase(repository)
    }

    @Test
    fun `given fii code when invoke is called then updates favorite status in repository`() = runTest {
        // Given
        val code = "TSTF11"
        val isFavorite = true
        coEvery { repository.setFavorite(code, isFavorite) } returns Unit

        // When
        useCase(code, isFavorite)

        // Then
        coVerify { repository.setFavorite(code, isFavorite) }
    }
}
