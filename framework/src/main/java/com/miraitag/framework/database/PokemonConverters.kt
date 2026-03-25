package com.miraitag.framework.database

import androidx.room.TypeConverter
import com.miraitag.pokedex.data.entities.PokemonEntity
import kotlinx.serialization.json.Json

class PokemonConverters {

    private val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromAbilities(value: List<PokemonEntity.Abilities>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toAbilities(value: String): List<PokemonEntity.Abilities> =
        json.decodeFromString(value)

    @TypeConverter
    fun fromForms(value: List<PokemonEntity.NameBase>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toForms(value: String): List<PokemonEntity.NameBase> =
        json.decodeFromString(value)

    @TypeConverter
    fun fromStats(value: List<PokemonEntity.Stats>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toStats(value: String): List<PokemonEntity.Stats> =
        json.decodeFromString(value)

    @TypeConverter
    fun fromTypes(value: List<PokemonEntity.Types>): String =
        json.encodeToString(value)

    @TypeConverter
    fun toTypes(value: String): List<PokemonEntity.Types> =
        json.decodeFromString(value)

    @TypeConverter
    fun fromSpecies(value: PokemonEntity.NameBase): String =
        json.encodeToString(value)

    @TypeConverter
    fun toSpecies(value: String): PokemonEntity.NameBase =
        json.decodeFromString(value)

    @TypeConverter
    fun fromSprites(value: PokemonEntity.Sprites): String =
        json.encodeToString(value)

    @TypeConverter
    fun toSprites(value: String): PokemonEntity.Sprites =
        json.decodeFromString(value)

    @TypeConverter
    fun fromCries(value: PokemonEntity.Cries): String =
        json.encodeToString(value)

    @TypeConverter
    fun toCries(value: String): PokemonEntity.Cries =
        json.decodeFromString(value)
}