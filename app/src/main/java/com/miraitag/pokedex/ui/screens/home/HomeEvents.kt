package com.miraitag.pokedex.ui.screens.home

import com.miraitag.pokedex.ui.model.PokemonItem

sealed interface HomeEvents {
    data class NavigateToDetail(val pokemonItem: PokemonItem) : HomeEvents
    data class ShowError(val message: String) : HomeEvents
}