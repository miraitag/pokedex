package com.miraitag.pokedex.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class PokemonRepository() {

    suspend fun fetchPokemons(): List<Pokemon> = withContext(Dispatchers.IO) {
        delay(2000)
        pokemons
    }
}