package com.miraitag.pokedex.ui.screens.detail

import com.miraitag.pokedex.ui.model.Pokemon

data class DetailUiState(
    val pokemon: Pokemon? = null,
    val isLoading: Boolean = false
)