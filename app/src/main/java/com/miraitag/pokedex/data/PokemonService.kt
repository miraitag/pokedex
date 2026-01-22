package com.miraitag.pokedex.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("pokemon")
    suspend fun fetchPokemons(
        @Query("limit") limit: Int? = null,
        @Query("offset") offset: Int? = null
    ): Call<PokemonsResponse>

    @GET("pokemon/pokemonName")
    suspend fun fetchPokemonByName(
        @Path("pokemonName") pokemonName: String
    ): Call<PokemonByNameResponse>
}