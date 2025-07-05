package com.felipepalma14.cotajusta.ui.home

import android.annotation.SuppressLint
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.felipepalma14.cotajusta.data.remote.model.Fii
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test
import java.util.Locale

@Ignore("Temporarily ignoring due to issues with Compose testing setup")
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val testFii = Fii(
        symbol = "HGLG11",
        name = "CSHG Logística",
        lastPrice = 100.0,
        dividendYield = 7.5,
        change = 0.5,
        segment = "Logística",
        volume = 1000000.0,
        volumeAvg = 950000.0,
        priceOpen = 99.0,
        high = 101.0,
        low = 98.0,
        lastYearHigh = 120.0,
        lastYearLow = 90.0,
        closingPrice = 100.0,
        typeOfManagement = "Ativa",
        targetAudience = "Geral",
        shares = 1000000L
    )

    @Test
    fun `Given initial state When screen starts Then show loading indicator`() {
        // Given
        val state = HomeFiiState(isLoading = true)

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        // Then
        composeTestRule.onNode(hasTestTag("LoadingIndicator")).assertIsDisplayed()
        composeTestRule.onNodeWithText("Buscar fundos imobiliários").assertIsDisplayed()
    }

    @Test
    fun `Given error state When loading fails Then show error message`() {
        // Given
        val errorMessage = "Não foi possível carregar os fundos imobiliários"
        val state = HomeFiiState(error = errorMessage)

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun `Given empty search results When searching Then show no results message`() {
        // Given
        val state = HomeFiiState(
            searchQuery = "XYZ",
            isEmptySearch = true
        )

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText("Nenhum fundo imobiliário encontrado").assertIsDisplayed()
    }

    @Test
    fun `Given fund list When screen loads Then show fund details`() {
        // Given
        val state = HomeFiiState(
            fiis = listOf(testFii),
            filteredFiis = listOf(testFii)
        )

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        // Then
        composeTestRule.onNodeWithText(testFii.symbol).assertIsDisplayed()
        composeTestRule.onNodeWithText(testFii.name).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            String.format(Locale.getDefault(), "DY: %.2f%%", testFii.dividendYield)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            String.format(Locale.getDefault(), "R$ %.2f", testFii.lastPrice)
        ).assertIsDisplayed()
    }

    @Test
    fun `Given fund list When clicking on item Then navigate to details`() {
        // Given
        var navigatedFii: Fii? = null
        val state = HomeFiiState(
            fiis = listOf(testFii),
            filteredFiis = listOf(testFii)
        )

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    onFiiClick = { navigatedFii = it },
                    state = state,
                    onEvent = {}
                )
            }
        }

        composeTestRule.onNodeWithText(testFii.symbol).performClick()

        // Then
        assert(navigatedFii == testFii)
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @Test
    fun `Given fund list When searching Then filter results`() {
        // Given
        val viewModel = FakeHomeFiiViewModel(HomeFiiState())

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = viewModel.state.value,
                    onEvent = { event ->
                        when (event) {
                            is HomeFiiEvent.Search -> viewModel.searchFiis(event.query)
                            is HomeFiiEvent.ToggleFavorite -> viewModel.toggleFavorite(event.symbol)
                            HomeFiiEvent.ClearError -> viewModel.clearError()
                        }
                    },
                    onFiiClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Buscar fundos imobiliários")
            .performTextInput("HGLG")

        // Then
        assert(viewModel.lastSearchQuery == "HGLG")
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    @Test
    fun `Given fund item When clicking favorite Then toggle favorite status`() {
        // Given
        val viewModel = FakeHomeFiiViewModel(
            HomeFiiState(
                fiis = listOf(testFii),
                filteredFiis = listOf(testFii)
            )
        )

        // When
        composeTestRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = viewModel.state.value,
                    onEvent = { event ->
                        when (event) {
                            is HomeFiiEvent.Search -> viewModel.searchFiis(event.query)
                            is HomeFiiEvent.ToggleFavorite -> viewModel.toggleFavorite(event.symbol)
                            HomeFiiEvent.ClearError -> viewModel.clearError()
                        }
                    },
                    onFiiClick = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("FavoriteButton_${testFii.symbol}").performClick()

        // Then
        assert(viewModel.lastToggledSymbol == testFii.symbol)
    }
}
