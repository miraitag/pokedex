package com.miraitag.pokedex.framework.remote.api

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemon/{name}")
    suspend fun fetchPokemonByName(
        @Path("name") name: String
    ): PokemonByNameResult
}