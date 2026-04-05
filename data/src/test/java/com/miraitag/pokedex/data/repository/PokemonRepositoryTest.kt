package com.miraitag.pokedex.data.repository

import com.miraitag.pokedex.data.entities.PokemonEntity
import com.miraitag.pokedex.data.local.PokemonLocalDataSource
import com.miraitag.pokedex.data.remote.PokemonRemoteDataSource
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

class PokemonRepositoryTest {

    private val localDataSource: PokemonLocalDataSource = mockk()
    private val remoteDataSource: PokemonRemoteDataSource = mockk()
    private lateinit var repository: PokemonRepository

    @BeforeEach
    fun setUp() {
        every { localDataSource.allPokemons } returns flowOf(emptyList())
        repository = PokemonRepository(localDataSource, remoteDataSource)
    }

    @Test
    fun `allPokemons maps entities to domain models`() = runBlocking {
        // Given
        val pokemonEntity = createPokemonEntity("pikachu")
        every { localDataSource.allPokemons } returns flowOf(listOf(pokemonEntity))
        val repositoryWithPokemons = PokemonRepository(localDataSource, remoteDataSource)

        // When
        val result = repositoryWithPokemons.allPokemons.first()

        // Then
        assertEquals(1, result.size)
        assertEquals("pikachu", result[0].name)
        verify { localDataSource.allPokemons }
    }

    @Test
    fun `fetchAndSavePokemon calls remote and then local when name is not empty`() = runBlocking {
        // Given
        val name = "pikachu"
        val pokemonRemote = createPokemonEntity(name)
        coEvery { remoteDataSource.fetchPokemonByName(name) } returns pokemonRemote
        coEvery { localDataSource.savePokemons(any()) } returns Unit

        // When
        repository.fetchAndSavePokemon(name)

        // Then
        coVerify { remoteDataSource.fetchPokemonByName(name) }
        coVerify { localDataSource.savePokemons(listOf(pokemonRemote)) }
    }

    @Test
    fun `fetchAndSavePokemon does nothing when name is empty`() = runBlocking {
        // When
        repository.fetchAndSavePokemon("")

        // Then
        coVerify(exactly = 0) { remoteDataSource.fetchPokemonByName(any()) }
        coVerify(exactly = 0) { localDataSource.savePokemons(any()) }
    }

    @Test
    fun `findPokemonByName returns mapped pokemon from local`() = runBlocking {
        // Given
        val name = "pikachu"
        val pokemonEntity = createPokemonEntity(name)
        every { localDataSource.fetchPokemonByName(name) } returns flowOf(pokemonEntity)

        // When
        val result = repository.findPokemonByName(name).first()

        // Then
        assertEquals(name, result?.name)
        verify { localDataSource.fetchPokemonByName(name) }
    }

    @Test
    fun `toggleFavorite switches isFavorite and saves`() = runBlocking {
        // Given
        val name = "pikachu"
        val pokemonEntity = createPokemonEntity(name, isFavorite = false)
        every { localDataSource.fetchPokemonByName(name) } returns flowOf(pokemonEntity)
        coEvery { localDataSource.savePokemons(any()) } returns Unit

        // When
        repository.toggleFavorite(name)

        // Then
        coVerify { 
            localDataSource.savePokemons(withArg { 
                assertEquals(true, it[0].isFavorite)
            }) 
        }
    }

    private fun createPokemonEntity(name: String, isFavorite: Boolean = false) = PokemonEntity(
        id = 1,
        name = name,
        baseExperience = 100,
        height = 10,
        isDefault = true,
        order = 1,
        weight = 100,
        abilities = emptyList(),
        forms = emptyList(),
        locationAreaEncounters = "",
        species = PokemonEntity.NameBase("", ""),
        sprites = PokemonEntity.Sprites(),
        cries = PokemonEntity.Cries(),
        stats = emptyList(),
        types = emptyList(),
        isFavorite = isFavorite
    )
}
