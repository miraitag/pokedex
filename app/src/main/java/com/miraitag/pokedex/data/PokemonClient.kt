package com.miraitag.pokedex.data

import com.miraitag.pokedex.BuildConfig
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

object PokemonClient {

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(::apiKeyQuery)
        .build()


    val instance = Retrofit.Builder()
        .baseUrl("https://pokeapi.co/api/v2/")
        .client(okHttpClient)
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create<PokemonService>()
}

private fun apiKeyQuery(chain: Interceptor.Chain) = chain.proceed(
    chain
        .request()
        .newBuilder()
        .url(
            chain.request().url
                .newBuilder()
                .addQueryParameter("apiKey", BuildConfig.POKEMONS_API_KEY)
                .build()
        )
        .build()
)