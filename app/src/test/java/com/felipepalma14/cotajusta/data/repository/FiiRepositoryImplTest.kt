package com.felipepalma14.cotajusta.data.repository

import com.felipepalma14.cotajusta.data.local.FiiLocalDataSource
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.data.remote.model.Fii
import com.felipepalma14.cotajusta.data.remote.model.FiiResponse
import com.felipepalma14.cotajusta.data.remote.FiiRemoteDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FiiRepositoryImplTest {
    private lateinit var remoteDataSource: FiiRemoteDataSource
    private lateinit var localDataSource: FiiLocalDataSource
    private lateinit var repository: FiiRepositoryImpl

    private val testFiiEntity = FiiEntity(
        code = "TEST11",
        name = "Test FII",
        change = 0.0,
        closingPrice = 100.0,
        dividendYield = 0.05,
        high = 101.0,
        lastPrice = 100.0,
        lastYearHigh = 110.0,
        lastYearLow = 90.0,
        low = 99.0,
        priceOpen = 100.0,
        segment = "Commercial",
        shares = 1000L,
        targetAudience = "General",
        typeOfManagement = "Active",
        volume = 10000.0,
        volumeAvg = 9500.0,
        isFavorite = false
    )

    @Before
    fun setUp() {
        remoteDataSource = mockk()
        localDataSource = mockk()
        repository = FiiRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `when getFiis is called with success then returns flow of success result`() = runTest {
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
        coEvery { remoteDataSource.fetchFii() } returns flow { emit(Result.success(response)) }

        // When
        val result = repository.getFiis().first()

        // Then
        assertTrue(result.isSuccess)
        assertEquals(response, result.getOrNull())
    }

    @Test
    fun `when getFiis is called with error then returns flow of failure result`() = runTest {
        // Given
        val exception = RuntimeException("API error")
        coEvery { remoteDataSource.fetchFii() } returns flow { emit(Result.failure(exception)) }

        // When
        val result = repository.getFiis().first()

        // Then
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `when getLocalFiis is called then returns flow of fiis from local source`() = runTest {
        // Given
        val fiis = listOf(testFiiEntity)
        coEvery { localDataSource.getAllFiis() } returns flow { emit(fiis) }

        // When
        val result = repository.getLocalFiis().first()

        // Then
        assertEquals(fiis, result)
    }

    @Test
    fun `when getFavoriteFiis is called then returns flow of favorite fiis`() = runTest {
        // Given
        val favoriteFiis = listOf(testFiiEntity.copy(isFavorite = true))
        coEvery { localDataSource.getFavorites() } returns flow { emit(favoriteFiis) }

        // When
        val result = repository.getFavoriteFiis().first()

        // Then
        assertEquals(favoriteFiis, result)
    }

    @Test
    fun `when setFavorite is called then updates favorite status in local source`() = runTest {
        // Given
        val code = "TEST11"
        val isFavorite = true
        coEvery { localDataSource.setFavorite(code, isFavorite) } returns Unit

        // When
        repository.setFavorite(code, isFavorite)

        // Then
        coVerify { localDataSource.setFavorite(code, isFavorite) }
    }

    @Test
    fun `when insertFiis is called then saves to local source`() = runTest {
        // Given
        val fiis = listOf(testFiiEntity)
        coEvery { localDataSource.insertAll(fiis) } returns Unit

        // When
        repository.insertFiis(fiis)

        // Then
        coVerify { localDataSource.insertAll(fiis) }
    }
}
