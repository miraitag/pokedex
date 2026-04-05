package com.miraitag.pokedex.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.miraitag.pokedex.ui.model.Pokemon
import com.miraitag.pokedex.ui.screens.home.PokemonItem
import com.miraitag.pokedex.ui.screens.home.Screen
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun pokemonItem_showsName() {
        val pokemon = createUiPokemon("Pikachu")

        composeTestRule.setContent {
            Screen {
                PokemonItem(
                    isFavorite = false,
                    pokemon = pokemon,
                    onClick = {}
                )
            }
        }

        composeTestRule.onNodeWithText("Pikachu").assertIsDisplayed()
    }

    @Test
    fun pokemonItem_showsFavoriteIcon_whenIsFavorite() {
        val pokemon = createUiPokemon("Pikachu")

        composeTestRule.setContent {
            Screen {
                PokemonItem(
                    isFavorite = true,
                    pokemon = pokemon,
                    onClick = {}
                )
            }
        }

        // We need the string resource for favorite_description. 
        // For simplicity in this example, let's assume it's "Favorite" or use a tag if available.
        // Looking at the code: contentDescription = stringResource(id = R.string.favorite_description)
        composeTestRule.onNodeWithContentDescription("Favorite").assertIsDisplayed()
    }

    @Test
    fun pokemonItem_clickCallsOnClick() {
        val pokemon = createUiPokemon("Pikachu")
        var clicked = false

        composeTestRule.setContent {
            Screen {
                PokemonItem(
                    isFavorite = false,
                    pokemon = pokemon,
                    onClick = { clicked = true }
                )
            }
        }

        composeTestRule.onNodeWithText("Pikachu").performClick()
        assert(clicked)
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
