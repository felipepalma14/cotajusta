package com.felipepalma14.cotajusta.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.felipepalma14.cotajusta.data.local.entity.FiiEntity
import com.felipepalma14.cotajusta.data.remote.model.FiiResponse
import com.felipepalma14.cotajusta.domain.usecase.GetFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.GetLocalFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.ToggleFavoriteFiiUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeFiiViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val getFiisUseCase: GetFiisUseCase = mockk()
    private val getLocalFiisUseCase: GetLocalFiisUseCase = mockk()
    private val toggleFavoriteFiiUseCase: ToggleFavoriteFiiUseCase = mockk()
    private lateinit var viewModel: HomeFiiViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = HomeFiiViewModel(getFiisUseCase, getLocalFiisUseCase, toggleFavoriteFiiUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createTestFiiEntity(
        code: String,
        name: String,
        change: Double = 0.5
    ) = FiiEntity(
        code = code,
        name = name,
        change = change,
        closingPrice = 100.0,
        dividendYield = 7.5,
        high = 102.0,
        lastPrice = 101.0,
        lastYearHigh = 105.0,
        lastYearLow = 95.0,
        low = 99.0,
        priceOpen = 100.5,
        segment = "Logística",
        shares = 1000000L,
        targetAudience = "Geral",
        typeOfManagement = "Ativa",
        volume = 1000000.0,
        volumeAvg = 950000.0
    )

    @Test
    fun `Given local data When loadFiis is called Then show fiis`() = runTest {
        // Given
        val localFiis = listOf(
            createTestFiiEntity("HGLG11", "CSHG Logística"),
            createTestFiiEntity("HGRE11", "CSHG Real Estate")
        )
        coEvery { getLocalFiisUseCase() } returns flowOf(localFiis)
        coEvery { getFiisUseCase() } returns flowOf(Result.failure(Exception()))

        // When
        viewModel.loadFiis()

        // Then
        with(viewModel.state.value) {
            assertFalse(isLoading)
            assertEquals(2, fiis.size)
            assertEquals(2, filteredFiis.size)
            assertNull(error)
        }
    }

    @Test
    fun `Given remote error and no local data When loadFiis is called Then show error state`() = runTest {
        // Given
        coEvery { getLocalFiisUseCase() } returns flowOf(emptyList())
        coEvery { getFiisUseCase() } returns flowOf(Result.failure(Exception()))

        // When
        viewModel.loadFiis()

        // Then
        with(viewModel.state.value) {
            assertFalse(isLoading)
            assertTrue(fiis.isEmpty())
            assertTrue(filteredFiis.isEmpty())
            assertEquals("Não foi possível atualizar os dados. Tente novamente mais tarde.", error)
        }
    }

    @Test
    fun `Given search by fund type When searchFiis is called Then filter correctly`() = runTest {
        // Given
        val localFiis = listOf(
            createTestFiiEntity("HGLG11", "CSHG Logística"),
            createTestFiiEntity("HGRE11", "CSHG Real Estate"),
            createTestFiiEntity("VISC11", "Vinci Shopping")
        )
        coEvery { getLocalFiisUseCase() } returns flowOf(localFiis)
        coEvery { getFiisUseCase() } returns flowOf(Result.success(FiiResponse(emptyList())))

        viewModel.loadFiis()

        // When
        viewModel.searchFiis("Logística")

        // Then
        with(viewModel.state.value) {
            assertEquals(1, filteredFiis.size)
            assertEquals("HGLG11", filteredFiis.first().symbol)
            assertFalse(isEmptySearch)
        }
    }

    @Test
    fun `Given toggle favorite When called Then update local data`() = runTest {
        // Given
        val localFii = createTestFiiEntity("HGLG11", "CSHG Logística")
        coEvery { getLocalFiisUseCase() } returns flowOf(listOf(localFii))
        coEvery { toggleFavoriteFiiUseCase("HGLG11", true) } returns Unit
        coEvery { getFiisUseCase() } returns flowOf(Result.success(FiiResponse(emptyList())))

        // When
        viewModel.loadFiis()
        viewModel.toggleFavorite("HGLG11")

        // Then
        assertEquals("HGLG11", viewModel.state.value.fiis.first().symbol)
    }

    @Test
    fun `Given error toggling favorite When toggleFavorite is called Then show error message`() = runTest {
        // Given
        coEvery { toggleFavoriteFiiUseCase("HGLG11", true) } throws Exception()

        // When
        viewModel.toggleFavorite("HGLG11")

        // Then
        assertEquals("Erro ao atualizar favorito", viewModel.state.value.error)
    }
}
