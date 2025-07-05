package com.felipepalma14.cotajusta.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.felipepalma14.cotajusta.R
import com.felipepalma14.cotajusta.data.remote.model.Fii
import com.felipepalma14.cotajusta.ui.home.components.FiiItem
import com.felipepalma14.cotajusta.ui.home.components.SearchBox

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: HomeFiiState,
    onEvent: (HomeFiiEvent) -> Unit,
    onFiiClick: (Fii) -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        SearchBox(
            query = state.searchQuery,
            onQueryChange = { query ->
                onEvent(HomeFiiEvent.Search(query))
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(
                    modifier = Modifier.testTag("LoadingIndicator")
                )
                state.error != null -> Text(
                    text = state.error ?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                state.isEmptySearch -> Text(
                    text = stringResource(R.string.no_results_found),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )
                else -> LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        items = state.filteredFiis,
                        key = { it.symbol }
                    ) { fii ->
                        FiiItem(
                            fii = fii,
                            onFavoriteClick = {
                                onEvent(HomeFiiEvent.ToggleFavorite(fii.symbol))
                            },
                            onClick = { onFiiClick(fii) },
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, heightDp = 500)
@Composable
private fun HomeScreenPreview() {
    HomePreview(state = HomeFiiState(
        fiis = listOf(
            Fii(
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
            ),
            Fii(
                symbol = "XPLG11",
                name = "XP Log",
                lastPrice = 120.0,
                dividendYield = 8.2,
                change = -1.2,
                segment = "Logística",
                volume = 800000.0,
                volumeAvg = 750000.0,
                priceOpen = 121.0,
                high = 122.0,
                low = 119.0,
                lastYearHigh = 130.0,
                lastYearLow = 95.0,
                closingPrice = 120.0,
                typeOfManagement = "Ativa",
                targetAudience = "Geral",
                shares = 2000000L
            )
        ),
        filteredFiis = listOf(
            Fii(
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
        ),
        searchQuery = "HGLG"
    ))
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenLoadingPreview() {
    HomePreview(state = HomeFiiState(isLoading = true))
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenErrorPreview() {
    HomePreview(state = HomeFiiState(error = "Não foi possível carregar os fundos imobiliários"))
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptySearchPreview() {
    HomePreview(state = HomeFiiState(searchQuery = "XYZ", isEmptySearch = true))
}

@Composable
private fun HomePreview(state: HomeFiiState) {
    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SearchBox(
                query = state.searchQuery,
                onQueryChange = {}
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    state.isLoading -> CircularProgressIndicator()
                    state.error != null -> Text(
                        text = state.error,
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    state.isEmptySearch -> Text(
                        text = stringResource(R.string.no_results_found),
                        style = MaterialTheme.typography.bodyLarge,
                        textAlign = TextAlign.Center
                    )
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = state.filteredFiis,
                            key = { it.symbol }
                        ) { fii ->
                            FiiItem(
                                fii = fii,
                                onFavoriteClick = {},
                                onClick = {},
                                modifier = Modifier.padding(vertical = 4.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}
