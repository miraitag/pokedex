package com.miraitag.pokedex.ui.mappers

import com.miraitag.pokedex.ui.model.PokemonItem as UiModel
import com.miraitag.pokedex.data.PokemonItem as DataModel

fun DataModel.toUiModel() = UiModel(
    name = name,
    image = sprites.other?.officialArtwork?.frontDefault ?: "",
    id = id,
    type = types.firstOrNull()?.type?.name ?: "normal"
)