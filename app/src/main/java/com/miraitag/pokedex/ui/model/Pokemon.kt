package com.miraitag.pokedex.ui.model

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val name: String,
    val image: String,
    val id: Int,
    val type: String,
    val abilities: String,
    val forms: String,
    val weight: String,
    val sprites: List<String?>,
    val isFavorite: Boolean
)