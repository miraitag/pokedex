package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository
import com.miraitag.pokedex.domain.Pokemon
import kotlinx.coroutines.flow.Flow

class FindPokemonByNameUseCase(
    private val pokemonRepository: PokemonRepository
) {
    operator fun invoke(name: String): Flow<Pokemon?> = pokemonRepository.findPokemonByName(name)
}