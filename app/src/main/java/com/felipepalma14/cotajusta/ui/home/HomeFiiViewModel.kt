package com.felipepalma14.cotajusta.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.felipepalma14.cotajusta.data.remote.model.Fii
import com.felipepalma14.cotajusta.domain.model.toFii
import com.felipepalma14.cotajusta.domain.usecase.GetFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.GetLocalFiisUseCase
import com.felipepalma14.cotajusta.domain.usecase.ToggleFavoriteFiiUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFiiViewModel @Inject constructor(
    private val getFiisUseCase: GetFiisUseCase,
    private val getLocalFiisUseCase: GetLocalFiisUseCase,
    private val toggleFavoriteFiiUseCase: ToggleFavoriteFiiUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(HomeFiiState())
    val state: StateFlow<HomeFiiState> = _state.asStateFlow()

    init {
        loadFiis()
    }

    fun loadFiis() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            try {
                // First load local data
                val localFiis = getLocalFiisUseCase().first()
                val mappedLocalFiis = localFiis.map { entity -> entity.toFii() }
                _state.update {
                    it.copy(
                        isLoading = false,
                        fiis = mappedLocalFiis,
                        filteredFiis = mappedLocalFiis
                    )
                }

                // Then try to fetch remote data to update local cache
                try {
                    val remoteResult = getFiisUseCase().first()
                    remoteResult.onSuccess { response ->
                        if (response.fiis.isNotEmpty()) {
                            _state.update { currentState ->
                                currentState.copy(
                                    fiis = response.fiis,
                                    filteredFiis = if (currentState.searchQuery.isEmpty()) {
                                        response.fiis
                                    } else {
                                        filterFiis(response.fiis, currentState.searchQuery)
                                    }
                                )
                            }
                        }
                    }.onFailure { throwable ->
                        // If remote fetch fails but we have local data, don't show error
                        if (localFiis.isEmpty()) {
                            _state.update {
                                it.copy(error = "Não foi possível atualizar os dados. Tente novamente mais tarde.")
                            }
                        }
                    }
                } catch (_: Exception) {
                    // Handle any other errors in the remote fetch
                    if (localFiis.isEmpty()) {
                        _state.update {
                            it.copy(error = "Não foi possível atualizar os dados. Tente novamente mais tarde.")
                        }
                    }
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = "Erro ao carregar os fundos imobiliários. Verifique sua conexão."
                    )
                }
            }
        }
    }

    private fun filterFiis(fiis: List<Fii>, query: String): List<Fii> {
        return fiis.filter { fii ->
            fii.name.contains(query, ignoreCase = true) ||
            fii.symbol.contains(query, ignoreCase = true)
        }
    }

    fun searchFiis(query: String) {
        viewModelScope.launch {
            val filteredList = filterFiis(state.value.fiis, query)
            _state.update {
                it.copy(
                    searchQuery = query,
                    filteredFiis = filteredList,
                    isEmptySearch = filteredList.isEmpty() && query.isNotEmpty()
                )
            }
        }
    }

    fun toggleFavorite(symbol: String) {
        viewModelScope.launch {
            try {
                toggleFavoriteFiiUseCase(symbol, true)
                // Refresh the list to reflect the changes
                val updatedFiis = getLocalFiisUseCase().first().map { it.toFii() }
                _state.update { currentState ->
                    currentState.copy(
                        fiis = updatedFiis,
                        filteredFiis = if (currentState.searchQuery.isEmpty()) {
                            updatedFiis
                        } else {
                            filterFiis(updatedFiis, currentState.searchQuery)
                        }
                    )
                }
            } catch (_: Exception) {
                _state.update {
                    it.copy(error = "Erro ao atualizar favorito")
                }
            }
        }
    }

    fun clearError() {
        _state.update { it.copy(error = null) }
    }
}
