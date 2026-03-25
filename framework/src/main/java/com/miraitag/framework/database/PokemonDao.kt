package com.miraitag.framework.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miraitag.pokedex.data.entities.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon WHERE name = :name")
    fun getPokemonByName(name: String): Flow<PokemonEntity?>

    @Query("SELECT * FROM pokemon")
    fun getAllPokemons(): Flow<List<PokemonEntity>>

    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun countPokemons(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePokemons(pokemonEntity: List<PokemonEntity>)
}