package com.miraitag.pokedex.data

class PokemonRepository() {

    suspend fun fetchPokemonByName(name: String): PokemonItem {
        val response = PokemonClient.instance.fetchPokemonByNameOrId(name)
        return response.toDataModel()
    }
}
private fun PokemonByNameOrIdResponse.toDataModel(): PokemonItem  {
    return PokemonItem(
        id = id,
        name = name,
        baseExperience = baseExperience,
        height = height,
        isDefault = isDefault,
        order = order,
        weight = weight,
        abilities = abilities.map {
            PokemonItem.Abilities(
                isHidden = it.isHidden,
                slot = it.slot,
                ability = PokemonItem.NameBase(
                    name = it.ability.name,
                    url = it.ability.url
                )
            )
        },
        forms = forms.map {
            PokemonItem.NameBase(
                name = it.name,
                url = it.url
            )
        },
        locationAreaEncounters = locationAreaEncounters,
        species = PokemonItem.NameBase(
            name = species.name,
            url = species.url
        ),
        sprites = PokemonItem.Sprites(
            backDefault = sprites.backDefault,
            frontDefault = sprites.frontDefault,
            frontShiny = sprites.frontShiny,
            other = PokemonItem.OtherSprites(
                officialArtwork = PokemonItem.OfficialArtwork(
                    frontDefault = sprites.other?.officialArtwork?.frontDefault
                )
            )
        ),
        cries = PokemonItem.Cries(
            latest = cries.latest,
            legacy = cries.legacy
        ),
        stats = stats.map {
            PokemonItem.Stats(
                baseStat = it.baseStat,
                effort = it.effort,
                stat = PokemonItem.NameBase(
                    name = it.stat.name,
                    url = it.stat.url
                )
            )
        },
        types = types.map {
            PokemonItem.Types(
                slot = it.slot,
                type = PokemonItem.NameBase(
                    name = it.type.name,
                    url = it.type.url
                )
            )
        }
    )
}