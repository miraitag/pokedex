package com.miraitag.pokedex.ui.theme.color

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

val LocalPokemonColors = staticCompositionLocalOf {
    PokemonColors(
        text = Color.Unspecified,
        background = Color.Unspecified,
    )
}