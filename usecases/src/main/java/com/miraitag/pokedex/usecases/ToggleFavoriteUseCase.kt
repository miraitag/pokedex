package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository

class ToggleFavoriteUseCase(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(name: String) {
        pokemonRepository.toggleFavorite(name)
    }
}