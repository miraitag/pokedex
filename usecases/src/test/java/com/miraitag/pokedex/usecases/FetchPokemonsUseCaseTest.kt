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

class FetchPokemonsUseCaseTest {

    private val repository: PokemonRepository = mockk()
    private val useCase = FetchPokemonsUseCase(repository)

    @Test
    fun `invoke calls repository allPokemons`() = runBlocking {
        // Given
        val pokemons = listOf(mockk<Pokemon>())
        every { repository.allPokemons } returns flowOf(pokemons)

        // When
        val result = useCase().first()

        // Then
        assertEquals(pokemons, result)
    }
}
