package com.miraitag.pokedex.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonResponse(
    val id: Int,
    val name: String,
    @SerialName("base_experience") val baseExperience: Int,
    val height: Int,
    @SerialName("is_default") val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<Abilities>,
    val forms: List<NameBase>,
    @SerialName("location_area_encounters") val locationAreaEncounters: String,
    val species: NameBase,
    val sprites: Sprites,
    val cries: Cries,
    val stats: List<Stats>,
    val types: List<Types>,
)

@Serializable
data class Types(
    val slot: Int,
    val type: NameBase
)

@Serializable
data class Abilities(
    @SerialName("is_hidden") val isHidden: Boolean,
    val slot: Int,
    val ability: NameBase
)

@Serializable
data class Stats(
    @SerialName("base_stat") val baseStat: Int,
    val effort: Int,
    val stat: NameBase
)

@Serializable
data class Sprites(
    @SerialName("back_default") val backDefault: String? = null,
    @SerialName("front_default") val frontDefault: String? = null,
    @SerialName("front_shiny") val frontShiny: String? = null,
    val other: OtherSprites? = null
)

@Serializable
data class OtherSprites(
    @SerialName("official-artwork") val officialArtwork: OfficialArtwork? = null
)

@Serializable
data class OfficialArtwork(
    @SerialName("front_default") val frontDefault: String? = null
)

@Serializable
data class Cries(
    val latest: String? = null,
    val legacy: String? = null
)

@Serializable
data class NameBase(
    val name: String,
    val url: String
)