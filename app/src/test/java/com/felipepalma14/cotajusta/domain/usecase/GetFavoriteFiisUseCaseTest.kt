package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetFavoriteFiisUseCaseTest {
    private lateinit var repository: FiiRepository
    private lateinit var useCase: GetFavoriteFiisUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetFavoriteFiisUseCase(repository)
    }

    @Test
    fun `given favorite fiis when invoke is called then returns favorite fiis from repository`() = runTest {
        // Given
        val favoriteFii = createTestFiiEntity(isFavorite = true)
        val expectedFiis = listOf(favoriteFii)
        coEvery { repository.getFavoriteFiis() } returns flowOf(expectedFiis)

        // When
        val result = useCase()
        val emitted = mutableListOf<List<FiiEntity>>()
        result.collect { emitted.add(it) }

        // Then
        assertEquals(expectedFiis, emitted.first())
    }

    private fun createTestFiiEntity(isFavorite: Boolean = true) = FiiEntity(
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
        isFavorite = isFavorite
    )
}
