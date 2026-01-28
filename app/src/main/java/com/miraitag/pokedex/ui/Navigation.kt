package com.miraitag.pokedex.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.miraitag.pokedex.ui.model.PokemonItem
import com.miraitag.pokedex.ui.screens.detail.DetailScreen
import com.miraitag.pokedex.ui.screens.home.HomeScreen
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey
@Serializable
data class Detail(val pokemon: PokemonItem) : NavKey

@Composable
fun NavigationScreen() {
    val backStack = rememberNavBackStack(Home)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            // 1. Otorga capacidad de guardar estado de UI (Scroll, etc.)
            rememberSaveableStateHolderNavEntryDecorator(),
            // 2. Otorga almacenamiento de ViewModels (para que vivan y mueran con la pantalla)
            rememberViewModelStoreNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<Home> {
                HomeScreen(
                    onPokemonClick = {
                        backStack.add(Detail(it))
                    }
                )
            }
            entry<Detail> {
                DetailScreen(
                    pokemon = it.pokemon,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}