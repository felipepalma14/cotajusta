package com.felipepalma14.cotajusta.ui.home

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.felipepalma14.cotajusta.data.remote.model.Fii
import org.junit.Rule
import org.junit.Test
import java.util.Locale

class HomeScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

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
    fun givenLoadingStateWhenStartsThenShowIndicator() {
        val state = HomeFiiState(isLoading = true)
        composeRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        composeRule.onNodeWithText("Buscar fundos imobiliários").assertIsDisplayed()
    }

    @Test
    fun givenErrorStateWhenLoadsThenShowMessage() {
        val errorMessage = "Não foi possível carregar os fundos imobiliários"
        val state = HomeFiiState(error = errorMessage)

        composeRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        composeRule.onNodeWithText(errorMessage).assertIsDisplayed()
    }

    @Test
    fun givenEmptySearchWhenSearchingThenShowMessage() {
        val state = HomeFiiState(searchQuery = "XYZ", isEmptySearch = true)

        composeRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        composeRule.onNodeWithText("Nenhum fundo imobiliário encontrado").assertIsDisplayed()
    }

    @Test
    fun givenFundDataWhenLoadsThenShowDetails() {
        val state = HomeFiiState(
            fiis = listOf(testFii),
            filteredFiis = listOf(testFii)
        )

        composeRule.setContent {
            MaterialTheme {
                HomeScreen(
                    state = state,
                    onEvent = {}
                )
            }
        }

        composeRule.onNodeWithText(testFii.symbol).assertIsDisplayed()
        composeRule.onNodeWithText(testFii.name).assertIsDisplayed()
        composeRule.onNodeWithText(
            String.format(Locale.getDefault(), "DY: %.2f%%", testFii.dividendYield)
        ).assertIsDisplayed()
        composeRule.onNodeWithText(
            String.format(Locale.getDefault(), "R$ %.2f", testFii.lastPrice)
        ).assertIsDisplayed()
    }
}
