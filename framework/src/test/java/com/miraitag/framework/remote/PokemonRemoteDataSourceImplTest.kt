package com.miraitag.framework.remote

import com.miraitag.framework.remote.api.PokemonByNameResult
import com.miraitag.framework.remote.api.PokemonService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class PokemonRemoteDataSourceImplTest {

    private val pokemonService: PokemonService = mockk()
    private val dataSource = PokemonRemoteDataSourceImpl(pokemonService)

    @Test
    fun `fetchPokemonByName calls service and returns data model`() = runBlocking {
        // Given
        val name = "pikachu"
        val remoteResult = createPokemonByNameResult(name)
        coEvery { pokemonService.fetchPokemonByName(name) } returns remoteResult

        // When
        val result = dataSource.fetchPokemonByName(name)

        // Then
        assertEquals(name, result.name)
        assertEquals(remoteResult.id, result.id)
        coVerify { pokemonService.fetchPokemonByName(name) }
    }

    private fun createPokemonByNameResult(name: String) = PokemonByNameResult(
        id = 1,
        name = name,
        baseExperience = 112,
        height = 4,
        isDefault = true,
        order = 1,
        weight = 60,
        abilities = emptyList(),
        forms = emptyList(),
        locationAreaEncounters = "",
        species = PokemonByNameResult.NameBase("pikachu", ""),
        sprites = PokemonByNameResult.Sprites(null, null, null, null),
        cries = PokemonByNameResult.Cries(null, null),
        stats = emptyList(),
        types = emptyList()
    )
}
