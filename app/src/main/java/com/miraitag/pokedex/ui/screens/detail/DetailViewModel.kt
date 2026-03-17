package com.miraitag.pokedex.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraitag.pokedex.ui.mappers.toUiModel
import com.miraitag.pokedex.usecases.FindPokemonByNameUseCase
import com.miraitag.pokedex.usecases.ToggleFavoriteUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    pokemonName: String,
    findPokemonByNameUseCase: FindPokemonByNameUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val uiState: StateFlow<DetailUiState> = findPokemonByNameUseCase(name = pokemonName)
        .map { pokemon -> DetailUiState(pokemon = pokemon?.toUiModel()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState(isLoading = true)
        )

    fun onFavoritePokemon() {
        viewModelScope.launch {
            uiState.value.pokemon?.let {
                toggleFavoriteUseCase(name = it.name)
            }
        }
    }
}
