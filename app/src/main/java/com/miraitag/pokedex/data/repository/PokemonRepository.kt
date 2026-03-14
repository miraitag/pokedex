package com.miraitag.pokedex.data.repository

import com.miraitag.pokedex.data.local.datasource.PokemonLocalDataSource
import com.miraitag.pokedex.data.local.entities.toDataModel
import com.miraitag.pokedex.data.remote.datasource.PokemonRemoteDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PokemonRepository(
    private val localDataSource: PokemonLocalDataSource,
    private val remoteDataSource: PokemonRemoteDataSource
) {

    val allPokemons = localDataSource.allPokemons.map { entities ->
        entities.map { it.toDataModel() }
    }

    suspend fun fetchAndSavePokemon(name: String) {
        if (name.isNotEmpty()) {
            // Lógica Offline-first
            val remotePokemon = remoteDataSource.fetchPokemonByName(name)
            // Guardar en la base de datos local (SSOT)
            localDataSource.savePokemons(listOf(remotePokemon))
        }
    }

    fun findPokemonByName(name: String) = localDataSource.fetchPokemonByName(name).map {
        it?.toDataModel()
    }

    suspend fun toggleFavorite(name: String) {
        val pokemonEntity = localDataSource.fetchPokemonByName(name).first()
        if (pokemonEntity != null) {
            val updatedPokemon = pokemonEntity.copy(isFavorite = !pokemonEntity.isFavorite)
            localDataSource.savePokemons(listOf(updatedPokemon))
        }
    }
}