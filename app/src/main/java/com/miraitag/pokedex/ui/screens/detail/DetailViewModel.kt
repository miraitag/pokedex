package com.miraitag.pokedex.ui.screens.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraitag.pokedex.data.repository.PokemonRepository
import com.miraitag.pokedex.ui.mappers.toUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    pokemonName: String,
    private val repository: PokemonRepository
) : ViewModel() {

    val uiState: StateFlow<DetailUiState> = repository.findPokemonByName(pokemonName)
        .map { pokemon -> DetailUiState(pokemon = pokemon?.toUiModel()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = DetailUiState(isLoading = true)
        )

    fun onFavoritePokemon() {
        viewModelScope.launch(Dispatchers.IO) {
            uiState.value.pokemon?.let {
                repository.toggleFavorite(name = it.name)
            }
        }
    }
}
