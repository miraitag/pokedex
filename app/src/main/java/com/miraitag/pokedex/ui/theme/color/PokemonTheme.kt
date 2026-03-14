package com.miraitag.pokedex.ui.theme.color

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable

object PokemonTheme {
    val colors: PokemonColors
        @Composable
        @ReadOnlyComposable
        get() = LocalPokemonColors.current
}