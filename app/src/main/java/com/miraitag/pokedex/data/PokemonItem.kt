package com.miraitag.pokedex.data

import kotlinx.serialization.Serializable

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val image: String
)

val pokemons = (1..100).map {
    Pokemon(
        id = it,
        name = "pokemon $it",
        image = "https://picsum.photos/200/300?id=$it"
    )
}