package com.miraitag.pokedex.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonService {

    @GET("pokemonName")
    suspend fun fetchPokemons(
        @Path("pokemonName") pokemonName: String
    ): Call<PokemonResponse>
}