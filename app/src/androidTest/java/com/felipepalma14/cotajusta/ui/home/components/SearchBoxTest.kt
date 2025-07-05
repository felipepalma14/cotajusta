package com.felipepalma14.cotajusta.ui.home.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import org.junit.Rule
import org.junit.Test

class SearchBoxTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun givenEmptyQueryWhenDisplayedThenShowPlaceholder() {
        composeRule.setContent {
            MaterialTheme {
                SearchBox(
                    query = "",
                    onQueryChange = {}
                )
            }
        }

        composeRule.onNodeWithText("Buscar fundos imobiliários")
            .assertIsDisplayed()
    }

    @Test
    fun givenQueryTextWhenTypingThenTriggerCallback() {
        var searchQuery = ""

        composeRule.setContent {
            MaterialTheme {
                SearchBox(
                    query = "",
                    onQueryChange = { searchQuery = it }
                )
            }
        }

        composeRule.onNodeWithText("Buscar fundos imobiliários")
            .performTextInput("HGLG")

        assert(searchQuery == "HGLG")
    }

    @Test
    fun givenExistingQueryWhenDisplayedThenShowQuery() {
        val query = "HGLG"

        composeRule.setContent {
            MaterialTheme {
                SearchBox(
                    query = query,
                    onQueryChange = {}
                )
            }
        }

        composeRule.onNodeWithText(query)
            .assertIsDisplayed()
    }
}
