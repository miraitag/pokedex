package com.miraitag.pokedex.ui.screens.detail

import com.miraitag.pokedex.domain.Pokemon
import com.miraitag.pokedex.usecases.FindPokemonByNameUseCase
import com.miraitag.pokedex.usecases.ToggleFavoriteUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val findPokemonByNameUseCase: FindPokemonByNameUseCase = mockk()
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase = mockk()
    private lateinit var viewModel: DetailViewModel

    private val testDispatcher = StandardTestDispatcher()
    private val pokemonName = "Pikachu"

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { findPokemonByNameUseCase(pokemonName) } returns flowOf(createPokemon(pokemonName))
        viewModel = DetailViewModel(pokemonName, findPokemonByNameUseCase, toggleFavoriteUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initially has isLoading true`() = runTest {
        val viewModelInit = DetailViewModel(pokemonName, findPokemonByNameUseCase, toggleFavoriteUseCase)
        assertEquals(true, viewModelInit.uiState.value.isLoading)
    }

    @Test
    fun `uiState updates with pokemon details from use case`() = runTest {
        val job = launch { viewModel.uiState.collect() }
        advanceUntilIdle()

        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(pokemonName, viewModel.uiState.value.pokemon?.name)
        
        job.cancel()
    }

    @Test
    fun `onFavoritePokemon calls toggleFavoriteUseCase`() = runTest {
        // Given
        val pokemon = createPokemon(pokemonName)
        every { findPokemonByNameUseCase(pokemonName) } returns flowOf(pokemon)
        coEvery { toggleFavoriteUseCase(pokemonName) } returns Unit
        
        val job = launch { viewModel.uiState.collect() }
        advanceUntilIdle() // Ensure uiState is loaded

        // When
        viewModel.onFavoritePokemon()
        advanceUntilIdle()

        // Then
        coVerify { toggleFavoriteUseCase(pokemonName) }
        
        job.cancel()
    }

    private fun createPokemon(name: String) = Pokemon(
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
        species = Pokemon.NameBase("", ""),
        sprites = Pokemon.Sprites(),
        cries = Pokemon.Cries(),
        stats = emptyList(),
        types = emptyList(),
        isFavorite = false
    )
}
