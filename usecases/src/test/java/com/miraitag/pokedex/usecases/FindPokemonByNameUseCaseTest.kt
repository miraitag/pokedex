package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository
import com.miraitag.pokedex.domain.Pokemon
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class FindPokemonByNameUseCaseTest {

    private val repository: PokemonRepository = mockk()
    private val useCase = FindPokemonByNameUseCase(repository)

    @Test
    fun `invoke calls repository findPokemonByName`() = runBlocking {
        // Given
        val name = "pikachu"
        val pokemon = mockk<Pokemon>()
        every { repository.findPokemonByName(name) } returns flowOf(pokemon)

        // When
        val result = useCase(name).first()

        // Then
        assertEquals(pokemon, result)
    }
}
