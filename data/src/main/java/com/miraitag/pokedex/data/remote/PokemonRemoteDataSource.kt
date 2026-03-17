package com.miraitag.pokedex.data.remote

import com.miraitag.pokedex.data.entities.PokemonEntity

interface PokemonRemoteDataSource {
    suspend fun fetchPokemonByName(name: String): PokemonEntity
}