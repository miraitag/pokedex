package com.miraitag.pokedex.data.local

import com.miraitag.pokedex.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow


interface PokemonLocalDataSource {
    val allPokemons: Flow<List<PokemonEntity>>
    fun fetchPokemonByName(name: String): Flow<PokemonEntity?>

    suspend fun isPokemonEmpty(): Boolean

    suspend fun savePokemons(pokemonEntities: List<PokemonEntity>)
}