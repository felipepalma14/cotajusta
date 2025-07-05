package com.felipepalma14.cotajusta.ui.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeHomeFiiViewModel(initialState: HomeFiiState) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state: StateFlow<HomeFiiState> = _state.asStateFlow()

    var lastSearchQuery: String? = null
        private set

    var lastToggledSymbol: String? = null
        private set

    fun searchFiis(query: String) {
        lastSearchQuery = query
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun toggleFavorite(symbol: String) {
        lastToggledSymbol = symbol
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
