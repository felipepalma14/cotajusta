package com.felipepalma14.cotajusta.ui.home

import com.felipepalma14.cotajusta.data.remote.model.Fii

data class HomeFiiState(
    val isLoading: Boolean = false,
    val fiis: List<Fii> = emptyList(),
    val filteredFiis: List<Fii> = emptyList(),
    val error: String? = null,
    val searchQuery: String = "",
    val isEmptySearch: Boolean = false
)
