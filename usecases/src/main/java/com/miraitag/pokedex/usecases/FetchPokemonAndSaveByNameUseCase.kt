package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository

class FetchPokemonAndSaveByNameUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(name: String) = pokemonRepository.fetchAndSavePokemon(name)
}