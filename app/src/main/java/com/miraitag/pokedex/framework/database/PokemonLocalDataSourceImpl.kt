package com.miraitag.pokedex.framework.database

import com.miraitag.pokedex.data.local.PokemonLocalDataSource
import com.miraitag.pokedex.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

class PokemonLocalDataSourceImpl(
    private val pokemonDao: PokemonDao
) : PokemonLocalDataSource {

    override val allPokemons: Flow<List<PokemonEntity>> = pokemonDao.getAllPokemons()

    override fun fetchPokemonByName(name: String): Flow<PokemonEntity?> = pokemonDao.getPokemonByName(name)

    override suspend fun isPokemonEmpty(): Boolean = pokemonDao.countPokemons() == 0

    override suspend fun savePokemons(pokemonEntities: List<PokemonEntity>) = pokemonDao.savePokemons(pokemonEntities)
}