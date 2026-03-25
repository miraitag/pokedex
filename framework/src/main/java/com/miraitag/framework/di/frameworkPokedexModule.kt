package com.miraitag.framework.di

import androidx.room.Room
import com.miraitag.framework.database.PokemonDataBase
import com.miraitag.framework.database.PokemonLocalDataSourceImpl
import com.miraitag.framework.remote.PokemonRemoteDataSourceImpl
import com.miraitag.framework.remote.api.PokemonClient
import com.miraitag.pokedex.data.local.PokemonLocalDataSource
import com.miraitag.pokedex.data.remote.PokemonRemoteDataSource
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val frameworkPokedexModule = module {
    single {
        Room.databaseBuilder(
            context = get(),
            klass = PokemonDataBase::class.java,
            name = "pokemon_database"
        ).build()
    }
    single { get<PokemonDataBase>().pokemonDao() }
    single {
        PokemonClient(
            apiKey = get(named("apiKey")),
            apiUrl = "https://pokeapi.co/api/v2/"
        ).instance
    }
    factoryOf(::PokemonRemoteDataSourceImpl) bind PokemonRemoteDataSource::class
    factoryOf(::PokemonLocalDataSourceImpl) bind PokemonLocalDataSource::class
}