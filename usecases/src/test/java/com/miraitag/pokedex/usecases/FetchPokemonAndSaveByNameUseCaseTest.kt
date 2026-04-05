package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class FetchPokemonAndSaveByNameUseCaseTest {

    private val repository: PokemonRepository = mockk()
    private val useCase = FetchPokemonAndSaveByNameUseCase(repository)

    @Test
    fun `invoke calls repository fetchAndSavePokemon`() = runBlocking {
        // Given
        val name = "pikachu"
        coEvery { repository.fetchAndSavePokemon(name) } returns Unit

        // When
        useCase(name)

        // Then
        coVerify { repository.fetchAndSavePokemon(name) }
    }
}
