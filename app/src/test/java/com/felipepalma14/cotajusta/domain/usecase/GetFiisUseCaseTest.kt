package com.felipepalma14.cotajusta.domain.usecase

import com.felipepalma14.cotajusta.data.model.Fii
import com.felipepalma14.cotajusta.data.model.FiiResponse
import com.felipepalma14.cotajusta.domain.repository.FiiRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class GetFiisUseCaseTest {
    private lateinit var repository: FiiRepository
    private lateinit var useCase: GetFiisUseCase

    @Before
    fun setUp() {
        repository = mockk()
        useCase = GetFiisUseCase(repository)
    }

    @Test
    fun `given successful repository response when invoke is called then returns success with fiis`() = runTest {
        // Given
        val fii = Fii(
            change = 1.0,
            closingPrice = 100.0,
            dividendYield = 0.05,
            high = 105.0,
            lastPrice = 102.0,
            lastYearHigh = 110.0,
            lastYearLow = 90.0,
            low = 99.0,
            name = "Test FII",
            priceOpen = 100.0,
            segment = "Segment",
            shares = 1000,
            symbol = "TSTF11",
            targetAudience = "General",
            typeOfManagement = "Active",
            volume = 10000.0,
            volumeAvg = 9500.0
        )
        val response = FiiResponse(listOf(fii))
        coEvery { repository.getFiis() } returns flowOf(Result.success(response))

        // When
        val result = useCase()
        val emitted = mutableListOf<Result<FiiResponse>>()
        result.collect { emitted.add(it) }

        // Then
        assertTrue(emitted.first().isSuccess)
        assertEquals(response, emitted.first().getOrNull())
    }

    @Test
    fun `given repository error when invoke is called then returns failure`() = runTest {
        // Given
        val exception = RuntimeException("Error")
        coEvery { repository.getFiis() } returns flowOf(Result.failure(exception))

        // When
        val result = useCase()
        val emitted = mutableListOf<Result<FiiResponse>>()
        result.collect { emitted.add(it) }

        // Then
        assertTrue(emitted.first().isFailure)
    }
}
