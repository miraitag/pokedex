package com.miraitag.framework.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.miraitag.pokedex.data.entities.PokemonEntity

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(PokemonConverters::class)
internal abstract class PokemonDataBase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}