package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository
import com.miraitag.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

class FetchPokemonsUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(): Flow<List<Pokemon>> = pokemonRepository.allPokemons
}