package com.miraitag.pokedex.ui.screens.home

import com.miraitag.pokedex.data.Pokemon

data class HomeUiState(
    val isLoading: Boolean = false,
    val pokemons: List<Pokemon> = emptyList(),
)