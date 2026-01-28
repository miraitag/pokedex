package com.miraitag.pokedex.data

import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {
    @GET("pokemon/{nameOrId}")
    suspend fun fetchPokemonByNameOrId(
        @Path("nameOrId") nameOrId: String
    ): PokemonByNameOrIdResponse
}