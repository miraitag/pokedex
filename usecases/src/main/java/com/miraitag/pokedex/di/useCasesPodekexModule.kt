package com.miraitag.pokedex.di

import com.miraitag.pokedex.usecases.FetchPokemonAndSaveByNameUseCase
import com.miraitag.pokedex.usecases.FetchPokemonsUseCase
import com.miraitag.pokedex.usecases.FindPokemonByNameUseCase
import com.miraitag.pokedex.usecases.ToggleFavoriteUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val useCasesPokedexModule = module {
    factoryOf(::FetchPokemonAndSaveByNameUseCase)
    factoryOf(::FetchPokemonsUseCase)
    factoryOf(::FindPokemonByNameUseCase)
    factoryOf(::ToggleFavoriteUseCase)
}