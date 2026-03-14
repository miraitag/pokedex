package com.miraitag.pokedex.data.local.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miraitag.pokedex.data.local.entities.PokemonConverters
import com.miraitag.pokedex.data.local.entities.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonConverters::class)
abstract class PokemonDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}