package com.miraitag.pokedex.ui.mappers

import com.miraitag.pokedex.domain.Pokemon as DataModel
import com.miraitag.pokedex.ui.model.Pokemon as UiModel

fun DataModel.toUiModel() = UiModel(
    name = name,
    image = sprites.other?.officialArtwork?.frontDefault ?: "",
    id = id,
    type = types.firstOrNull()?.type?.name ?: "normal",
    abilities = abilities.joinToString(separator = ", ") { it.ability.name },
    forms = forms.joinToString(separator = ", ") { it.name },
    weight = weight.toString(),
    sprites = listOf(sprites.frontDefault, sprites.backDefault),
    isFavorite = isFavorite
)