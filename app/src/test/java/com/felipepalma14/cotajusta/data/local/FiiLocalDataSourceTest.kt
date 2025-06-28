package com.felipepalma14.cotajusta.data.local

import com.felipepalma14.cotajusta.data.local.dao.FiiDao
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FiiLocalDataSourceTest {
    private lateinit var fiiDao: FiiDao
    private lateinit var localDataSource: FiiLocalDataSource

    @Before
    fun setUp() {
        fiiDao = mockk()
        localDataSource = FiiLocalDataSourceImpl(Dispatchers.Unconfined, fiiDao)
    }

    @Test
    fun `when getFavorites is called then returns favorite fiis from dao`() = runTest {
        // Given
        val favoriteFii = FiiEntity(
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
            isFavorite = true
        )
        val favoriteList = listOf(favoriteFii)
        coEvery { fiiDao.getFavorites() } returns flowOf(favoriteList)

        // When
        val result = localDataSource.getFavorites()

        // Then
        result.collect { fiis ->
            assertEquals(favoriteList, fiis)
        }
    }

    @Test
    fun `when setFavorite is called then updates fii favorite status in dao`() = runTest {
        // Given
        val code = "TEST11"
        val isFavorite = true
        coEvery { fiiDao.setFavorite(code, isFavorite) } returns Unit

        // When
        localDataSource.setFavorite(code, isFavorite)

        // Then
        coVerify { fiiDao.setFavorite(code, isFavorite) }
    }

    @Test
    fun `when getAllFiis is called then returns all fiis from dao`() = runTest {
        // Given
        val fii1 = FiiEntity(
            code = "TEST11",
            name = "Test FII 1",
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
        val fii2 = fii1.copy(
            code = "TEST12",
            name = "Test FII 2",
            isFavorite = true
        )
        val allFiis = listOf(fii1, fii2)
        coEvery { fiiDao.getAllFiis() } returns flowOf(allFiis)

        // When
        val result = localDataSource.getAllFiis()

        // Then
        result.collect { fiis ->
            assertEquals(allFiis, fiis)
        }
    }

    @Test
    fun `when insertAll is called then saves fiis to dao`() = runTest {
        // Given
        val fiis = listOf(
            FiiEntity(
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
        )
        coEvery { fiiDao.insertAll(fiis) } returns Unit

        // When
        localDataSource.insertAll(fiis)

        // Then
        coVerify { fiiDao.insertAll(fiis) }
    }
}
