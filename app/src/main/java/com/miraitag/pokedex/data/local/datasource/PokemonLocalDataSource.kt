package com.miraitag.pokedex.data.local.datasource

import com.miraitag.pokedex.data.local.datasource.database.PokemonDao
import com.miraitag.pokedex.data.local.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

class PokemonLocalDataSource(
    private val pokemonDao: PokemonDao
) {

    val allPokemons: Flow<List<PokemonEntity>> = pokemonDao.getAllPokemons()

    fun fetchPokemonByName(name: String): Flow<PokemonEntity?> = pokemonDao.getPokemonByName(name)

    suspend fun isPokemonEmpty(): Boolean = pokemonDao.countPokemons() == 0

    suspend fun savePokemons(pokemonEntities: List<PokemonEntity>) = pokemonDao.savePokemons(pokemonEntities)
}