package com.miraitag.pokedex.ui.screens.home

import androidx.compose.runtime.Immutable
import com.miraitag.pokedex.ui.model.Pokemon

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val limit: Int = 0,
    val pokemons: List<Pokemon> = emptyList(),
    val showMessageError: String? = null,
    val pokemonToNavigate: Pokemon? = null
)