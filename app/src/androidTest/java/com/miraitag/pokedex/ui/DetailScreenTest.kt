package com.miraitag.pokedex.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.miraitag.pokedex.ui.model.Pokemon
import com.miraitag.pokedex.ui.screens.detail.DetailScreen
import com.miraitag.pokedex.ui.screens.detail.DetailUiState
import com.miraitag.pokedex.ui.screens.detail.DetailViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val viewModel: DetailViewModel = mockk(relaxed = true)

    @Test
    fun detailScreen_showsPokemonProperties() {
        val pokemon = createUiPokemon("Pikachu")
        val uiState = DetailUiState(pokemon = pokemon, isLoading = false)
        
        every { viewModel.uiState } returns MutableStateFlow(uiState)

        composeTestRule.setContent {
            DetailScreen(
                pokemon = pokemon,
                viewModel = viewModel,
                onBack = {}
            )
        }

        composeTestRule.onNodeWithText("Abilities: ").assertIsDisplayed()
        composeTestRule.onNodeWithText("Static").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weight: ").assertIsDisplayed()
        composeTestRule.onNodeWithText("6.0 kg").assertIsDisplayed()
    }

    private fun createUiPokemon(name: String) = Pokemon(
        id = 1,
        name = name,
        image = "",
        type = "electric",
        abilities = "Static",
        forms = "Pikachu",
        weight = "6.0 kg",
        sprites = emptyList(),
        isFavorite = false
    )
}
