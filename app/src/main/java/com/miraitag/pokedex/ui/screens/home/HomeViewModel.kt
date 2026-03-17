package com.miraitag.pokedex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraitag.pokedex.ui.mappers.toUiModel
import com.miraitag.pokedex.usecases.FetchPokemonAndSaveByNameUseCase
import com.miraitag.pokedex.usecases.FetchPokemonsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    fetchPokemonsUseCase: FetchPokemonsUseCase,
    private val fetchPokemonAndSaveByNameUseCase: FetchPokemonAndSaveByNameUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())

    val uiState: StateFlow<HomeUiState> = combine(
        fetchPokemonsUseCase(),
        _uiState
    ) { pokemons, currentState ->
        val pokemonList = pokemons.map { it.toUiModel() }
        currentState.copy(
            pokemons = pokemonList,
            isLoading = false
        )
    }
        //.flowOn(Dispatchers.Default)
        .catch { exception ->
            _uiState.update {
                it.copy(
                    isLoading = false,
                    showMessageError = exception.message
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState(isLoading = true)
        )

    fun onAction(action: HomeEvents) {
        when (action) {
            is HomeEvents.ResetNavigation -> _uiState.update { it.copy(pokemonToNavigate = null) }
            is HomeEvents.ShowError -> _uiState.update { it.copy(showMessageError = action.message) }
        }
    }

    fun fetchPokemonAndSavePokemonByName(name: String) {
        viewModelScope.launch {
            fetchPokemonAndSaveByNameUseCase(name)
        }
    }
}