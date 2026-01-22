package com.miraitag.pokedex.data

import kotlinx.serialization.Serializable

@Serializable
data class PokemonsResponse(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Pokemons>
)

@Serializable
data class Pokemons(
    val name: String,
    val url: String
)