package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SaveFiisUseCaseTest {
    private lateinit var repository: FiiRepository
    private lateinit var useCase: SaveFiisUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = SaveFiisUseCase(repository)
    }

    @Test
    fun `given list of fiis when invoke is called then saves fiis in repository`() = runTest {
        // Given
        val fiiEntity = createTestFiiEntity()
        val fiisToSave = listOf(fiiEntity)
        coEvery { repository.insertFiis(fiisToSave) } returns Unit

        // When
        useCase(fiisToSave)

        // Then
        coVerify { repository.insertFiis(fiisToSave) }
    }

    private fun createTestFiiEntity() = FiiEntity(
        code = "TSTF11",
        name = "Test FII",
        change = 1.0,
        closingPrice = 100.0,
        dividendYield = 0.05,
        high = 105.0,
        lastPrice = 102.0,
        lastYearHigh = 110.0,
        lastYearLow = 90.0,
        low = 99.0,
        priceOpen = 100.0,
        segment = "Segment",
        shares = 1000,
        targetAudience = "General",
        typeOfManagement = "Active",
        volume = 10000.0,
        volumeAvg = 9500.0,
        isFavorite = false
    )
}
