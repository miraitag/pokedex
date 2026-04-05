package com.miraitag.pokedex.ui.screens.home

import com.miraitag.pokedex.domain.Pokemon
import com.miraitag.pokedex.usecases.FetchPokemonAndSaveByNameUseCase
import com.miraitag.pokedex.usecases.FetchPokemonsUseCase
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
class HomeViewModelTest {

    private val fetchPokemonsUseCase: FetchPokemonsUseCase = mockk()
    private val fetchPokemonAndSaveByNameUseCase: FetchPokemonAndSaveByNameUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        every { fetchPokemonsUseCase() } returns flowOf(emptyList())
        viewModel = HomeViewModel(fetchPokemonsUseCase, fetchPokemonAndSaveByNameUseCase)
    }

    @AfterEach
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `uiState initially has isLoading true`() = runTest {
        assertEquals(true, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `uiState updates with pokemons from use case`() = runTest {
        // Given
        val pokemons = listOf(createPokemon("Pikachu"))
        every { fetchPokemonsUseCase() } returns flowOf(pokemons)
        
        // Re-init to capture the new flow
        viewModel = HomeViewModel(fetchPokemonsUseCase, fetchPokemonAndSaveByNameUseCase)
        
        // We need to collect the stateFlow for it to update (SharingStarted.WhileSubscribed)
        val job = launch { viewModel.uiState.collect() }
        advanceUntilIdle()

        // Then
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertEquals(1, viewModel.uiState.value.pokemons.size)
        assertEquals("Pikachu", viewModel.uiState.value.pokemons[0].name)
        
        job.cancel()
    }

    @Test
    fun `fetchPokemonAndSavePokemonByName calls use case`() = runTest {
        // Given
        val name = "Bulbasaur"
        coEvery { fetchPokemonAndSaveByNameUseCase(name) } returns Unit

        // When
        viewModel.fetchPokemonAndSavePokemonByName(name)
        advanceUntilIdle()

        // Then
        coVerify { fetchPokemonAndSaveByNameUseCase(name) }
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
