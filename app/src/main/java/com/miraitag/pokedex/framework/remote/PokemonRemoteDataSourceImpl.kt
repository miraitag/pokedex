package com.miraitag.pokedex.framework.remote

import com.miraitag.pokedex.data.entities.PokemonEntity
import com.miraitag.pokedex.framework.remote.api.PokemonByNameResult
import com.miraitag.pokedex.framework.remote.api.PokemonService
import com.miraitag.pokedex.data.remote.PokemonRemoteDataSource

class PokemonRemoteDataSourceImpl(
    private val pokemonService: PokemonService
) : PokemonRemoteDataSource {

    override suspend fun fetchPokemonByName(name: String): PokemonEntity {
        val response = pokemonService.fetchPokemonByName(name = name)
        return response.toDataModel()
    }
}

private fun PokemonByNameResult.toDataModel(): PokemonEntity {
    return PokemonEntity(
        id = id,
        name = name,
        baseExperience = baseExperience,
        height = height,
        isDefault = isDefault,
        order = order,
        weight = weight,
        abilities = abilities.map {
            PokemonEntity.Abilities(
                isHidden = it.isHidden,
                slot = it.slot,
                ability = PokemonEntity.NameBase(
                    name = it.ability.name,
                    url = it.ability.url
                )
            )
        },
        forms = forms.map {
            PokemonEntity.NameBase(
                name = it.name,
                url = it.url
            )
        },
        locationAreaEncounters = locationAreaEncounters,
        species = PokemonEntity.NameBase(
            name = species.name,
            url = species.url
        ),
        sprites = PokemonEntity.Sprites(
            backDefault = sprites.backDefault,
            frontDefault = sprites.frontDefault,
            frontShiny = sprites.frontShiny,
            other = PokemonEntity.OtherSprites(
                officialArtwork = PokemonEntity.OfficialArtwork(
                    frontDefault = sprites.other?.officialArtwork?.frontDefault
                )
            )
        ),
        cries = PokemonEntity.Cries(
            latest = cries.latest,
            legacy = cries.legacy
        ),
        stats = stats.map {
            PokemonEntity.Stats(
                baseStat = it.baseStat,
                effort = it.effort,
                stat = PokemonEntity.NameBase(
                    name = it.stat.name,
                    url = it.stat.url
                )
            )
        },
        types = types.map {
            PokemonEntity.Types(
                slot = it.slot,
                type = PokemonEntity.NameBase(
                    name = it.type.name,
                    url = it.type.url
                )
            )
        },
        isFavorite = false
    )
}