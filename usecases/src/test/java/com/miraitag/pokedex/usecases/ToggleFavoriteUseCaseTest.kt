package com.miraitag.pokedex.usecases

import com.miraitag.pokedex.data.repository.PokemonRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class ToggleFavoriteUseCaseTest {

    private val repository: PokemonRepository = mockk()
    private val useCase = ToggleFavoriteUseCase(repository)

    @Test
    fun `invoke calls repository toggleFavorite`() = runBlocking {
        // Given
        val name = "pikachu"
        coEvery { repository.toggleFavorite(name) } returns Unit

        // When
        useCase(name)

        // Then
        coVerify { repository.toggleFavorite(name) }
    }
}
