package com.miraitag.pokedex.data.di

import com.miraitag.pokedex.data.repository.PokemonRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val dataPokemonModule = module {
    factoryOf(::PokemonRepository)
}