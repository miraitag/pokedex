package com.miraitag.pokedex.data

data class PokemonItem(
    val id: Int,
    val name: String,
    val baseExperience: Int,
    val height: Int,
    val isDefault: Boolean,
    val order: Int,
    val weight: Int,
    val abilities: List<Abilities>,
    val forms: List<NameBase>,
    val locationAreaEncounters: String,
    val species: NameBase,
    val sprites: Sprites,
    val cries: Cries,
    val stats: List<Stats>,
    val types: List<Types>
) {
    data class Types(
        val slot: Int,
        val type: NameBase
    )

    data class Abilities(
        val isHidden: Boolean,
        val slot: Int,
        val ability: NameBase
    )

    data class Stats(
        val baseStat: Int,
        val effort: Int,
        val stat: NameBase
    )

    data class Sprites(
        val backDefault: String? = null,
        val frontDefault: String? = null,
        val frontShiny: String? = null,
        val other: OtherSprites? = null
    )

    data class OtherSprites(
        val officialArtwork: OfficialArtwork? = null
    )

    data class OfficialArtwork(
        val frontDefault: String? = null
    )

    data class Cries(
        val latest: String? = null,
        val legacy: String? = null
    )

    data class NameBase(
        val name: String,
        val url: String
    )
}