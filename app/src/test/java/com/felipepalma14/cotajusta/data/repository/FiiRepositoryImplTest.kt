package com.felipepalma14.cotajusta.data.repository

import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.data.model.Fii
import com.felipepalma14.cotajusta.data.model.FiiResponse
import com.felipepalma14.cotajusta.data.remote.api.FiiApi
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class FiiRepositoryImplTest {
    private lateinit var api: FiiApi
    private lateinit var fiiDao: FiiDao
    private lateinit var repository: FiiRepositoryImpl

    @Before
    fun setUp() {
        api = mockk()
        fiiDao = mockk()
        repository = FiiRepositoryImpl(api, fiiDao)
    }

    @Test
    fun `given valid api response when getFiis is called then returns success with response`() = runTest {
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
        coEvery { api.getFiis() } returns flowOf(response)

        // When
        val result = repository.getFiis()
        val emitted = mutableListOf<Result<FiiResponse>>()
        result.collect { emitted.add(it) }

        // Then
        assertTrue(emitted.first().isSuccess)
        assertEquals(response, emitted.first().getOrNull())
    }

    @Test
    fun `given api error when getFiis is called then returns failure`() = runTest {
        // Given
        coEvery { api.getFiis() } throws RuntimeException("API error")

        // When
        val result = repository.getFiis()
        val emitted = mutableListOf<Result<FiiResponse>>()
        result.collect { emitted.add(it) }

        // Then
        assertTrue(emitted.first().isFailure)
    }

    @Test
    fun `given local fiis when getLocalFiis is called then returns data from dao`() = runTest {
        // Given
        val fiiEntity = createTestFiiEntity()
        coEvery { fiiDao.getAllFiis() } returns flowOf(listOf(fiiEntity))

        // When
        val result = repository.getLocalFiis()
        val emitted = mutableListOf<List<FiiEntity>>()
        result.collect { emitted.add(it) }

        // Then
        assertEquals(listOf(fiiEntity), emitted.first())
    }

    @Test
    fun `given fii list when insertFiis is called then calls dao insertAll`() = runTest {
        // Given
        val fiis = listOf<FiiEntity>()
        coEvery { fiiDao.insertAll(fiis) } returns Unit

        // When
        repository.insertFiis(fiis)

        // Then
        coVerify { fiiDao.insertAll(fiis) }
    }

    @Test
    fun `given fii code when setFavorite is called then updates favorite status in dao`() = runTest {
        // Given
        coEvery { fiiDao.setFavorite("TSTF11", true) } returns Unit

        // When
        repository.setFavorite("TSTF11", true)

        // Then
        coVerify { fiiDao.setFavorite("TSTF11", true) }
    }

    @Test
    fun `given favorite fiis when getFavoriteFiis is called then returns favorite fiis from dao`() = runTest {
        // Given
        val fiiEntity = createTestFiiEntity(isFavorite = true)
        coEvery { fiiDao.getFavorites() } returns flowOf(listOf(fiiEntity))

        // When
        val result = repository.getFavoriteFiis()
        val emitted = mutableListOf<List<FiiEntity>>()
        result.collect { emitted.add(it) }

        // Then
        assertEquals(listOf(fiiEntity), emitted.first())
    }

    private fun createTestFiiEntity(isFavorite: Boolean = false) = FiiEntity(
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
