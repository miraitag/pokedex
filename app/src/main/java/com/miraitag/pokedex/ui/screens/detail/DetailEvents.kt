package com.miraitag.pokedex.ui.screens.detail

import com.miraitag.pokedex.ui.model.PokemonItem

sealed interface DetailEvents {
    data class onFavoriteClicked(val pokemon: PokemonItem) : DetailEvents
}