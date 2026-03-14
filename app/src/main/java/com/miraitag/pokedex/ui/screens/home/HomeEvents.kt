package com.miraitag.pokedex.ui.screens.home

import com.miraitag.pokedex.ui.model.Pokemon

sealed interface HomeEvents {
    data class ResetNavigation(val pokemon: Pokemon) : HomeEvents
    data class ShowError(val message: String) : HomeEvents
}