package com.miraitag.pokedex.data.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.miraitag.pokedex.domain.Pokemon
import kotlinx.serialization.Serializable

@Entity(tableName = "pokemon")
data class PokemonEntity(
    @PrimaryKey val id: Int,
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
    val types: List<Types>,
    val isFavorite: Boolean
) {
    @Serializable
    data class Types(
        val slot: Int,
        val type: NameBase
    )

    @Serializable
    data class Abilities(
        val isHidden: Boolean,
        val slot: Int,
        val ability: NameBase
    )

    @Serializable
    data class Stats(
        val baseStat: Int,
        val effort: Int,
        val stat: NameBase
    )

    @Serializable
    data class Sprites(
        val backDefault: String? = null,
        val frontDefault: String? = null,
        val frontShiny: String? = null,
        val other: OtherSprites? = null
    )

    @Serializable
    data class OtherSprites(
        val officialArtwork: OfficialArtwork? = null
    )

    @Serializable
    data class OfficialArtwork(
        val frontDefault: String? = null
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
}

fun PokemonEntity.toDataModel(): Pokemon {
    return Pokemon(
        id = id,
        name = name,
        baseExperience = baseExperience,
        height = height,
        isDefault = isDefault,
        order = order,
        weight = weight,
        abilities = abilities.map {
            Pokemon.Abilities(
                isHidden = it.isHidden,
                slot = it.slot,
                ability = Pokemon.NameBase(
                    name = it.ability.name,
                    url = it.ability.url
                )
            )
        },
        forms = forms.map {
            Pokemon.NameBase(
                name = it.name,
                url = it.url
            )
        },
        locationAreaEncounters = locationAreaEncounters,
        species = Pokemon.NameBase(
            name = species.name,
            url = species.url
        ),
        sprites = Pokemon.Sprites(
            backDefault = sprites.backDefault,
            frontDefault = sprites.frontDefault,
            frontShiny = sprites.frontShiny,
            other = Pokemon.OtherSprites(
                officialArtwork = Pokemon.OfficialArtwork(
                    frontDefault = sprites.other?.officialArtwork?.frontDefault
                )
            )
        ),
        cries = Pokemon.Cries(
            latest = cries.latest,
            legacy = cries.legacy
        ),
        stats = stats.map {
            Pokemon.Stats(
                baseStat = it.baseStat,
                effort = it.effort,
                stat = Pokemon.NameBase(
                    name = it.stat.name,
                    url = it.stat.url
                )
            )
        },
        types = types.map {
            Pokemon.Types(
                slot = it.slot,
                type = Pokemon.NameBase(
                    name = it.type.name,
                    url = it.type.url
                )
            )
        },
        isFavorite = isFavorite,
    )
}