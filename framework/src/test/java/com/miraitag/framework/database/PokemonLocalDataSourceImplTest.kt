package com.miraitag.framework.database

import com.miraitag.pokedex.data.entities.PokemonEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PokemonLocalDataSourceImplTest {

    private val pokemonDao: PokemonDao = mockk()
    private lateinit var dataSource: PokemonLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        every { pokemonDao.getAllPokemons() } returns flowOf(emptyList())
        dataSource = PokemonLocalDataSourceImpl(pokemonDao)
    }

    @Test
    fun `allPokemons returns flow of pokemons from dao`() = runBlocking {
        // Given
        val pokemons = listOf(mockk<PokemonEntity>())
        every { pokemonDao.getAllPokemons() } returns flowOf(pokemons)
        // Since it's a val initialized in constructor, we need to recreate the dataSource if we change the mock behavior after init,
        // or just let the default emptyList be returned if we don't recreate.
        // But wait, the val allPokemons is assigned once.
        val dataSourceWithPokemons = PokemonLocalDataSourceImpl(pokemonDao)

        // When
        val result = dataSourceWithPokemons.allPokemons.first()

        // Then
        assertEquals(pokemons, result)
        verify { pokemonDao.getAllPokemons() }
    }

    @Test
    fun `fetchPokemonByName calls dao getPokemonByName`() = runBlocking {
        // Given
        val name = "pikachu"
        val pokemon = mockk<PokemonEntity>()
        every { pokemonDao.getPokemonByName(name) } returns flowOf(pokemon)

        // When
        val result = dataSource.fetchPokemonByName(name).first()

        // Then
        assertEquals(pokemon, result)
        verify { pokemonDao.getPokemonByName(name) }
    }

    @Test
    fun `isPokemonEmpty returns true when dao count is 0`() = runBlocking {
        // Given
        coEvery { pokemonDao.countPokemons() } returns 0

        // When
        val result = dataSource.isPokemonEmpty()

        // Then
        assertEquals(true, result)
        coVerify { pokemonDao.countPokemons() }
    }

    @Test
    fun `savePokemons calls dao savePokemons`() = runBlocking {
        // Given
        val pokemons = listOf(mockk<PokemonEntity>())
        coEvery { pokemonDao.savePokemons(pokemons) } returns Unit

        // When
        dataSource.savePokemons(pokemons)

        // Then
        coVerify { pokemonDao.savePokemons(pokemons) }
    }
}
