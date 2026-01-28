package com.miraitag.pokedex.ui.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class PokemonItem(
    val name: String,
    val image: String,
    val id: Int,
    val type: String
)