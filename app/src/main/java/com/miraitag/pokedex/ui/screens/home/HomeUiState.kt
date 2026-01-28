package com.miraitag.pokedex.ui.screens.home

import androidx.compose.runtime.Immutable
import com.miraitag.pokedex.ui.model.PokemonItem

@Immutable
data class HomeUiState(
    val isLoading: Boolean = false,
    val limit: Int = 0,
    val pokemons: List<PokemonItem> = emptyList(),
)