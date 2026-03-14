package com.miraitag.pokedex.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.miraitag.pokedex.App
import com.miraitag.pokedex.data.local.datasource.PokemonLocalDataSource
import com.miraitag.pokedex.data.remote.datasource.PokemonRemoteDataSource
import com.miraitag.pokedex.data.repository.PokemonRepository
import com.miraitag.pokedex.ui.model.Pokemon
import com.miraitag.pokedex.ui.screens.detail.DetailScreen
import com.miraitag.pokedex.ui.screens.detail.DetailViewModel
import com.miraitag.pokedex.ui.screens.home.HomeScreen
import com.miraitag.pokedex.ui.screens.home.HomeViewModel
import kotlinx.serialization.Serializable

@Serializable
data object Home : NavKey

@Serializable
data class Detail(val pokemon: Pokemon) : NavKey

@Composable
fun NavigationScreen() {
    val backStack = rememberNavBackStack(Home)
    val app = LocalContext.current.applicationContext as App
    val pokemonRepository = PokemonRepository(
        localDataSource = PokemonLocalDataSource(
            pokemonDao = app.db.pokemonDao()
        ),
        remoteDataSource = PokemonRemoteDataSource()
    )
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
                    onPokemonClick = { pokemon ->
                        backStack.add(Detail(pokemon = pokemon))
                    },
                    viewModel = viewModel { HomeViewModel(repository = pokemonRepository) }
                )
            }
            entry<Detail> {
                DetailScreen(
                    pokemon = it.pokemon,
                    viewModel = viewModel {
                        DetailViewModel(
                            repository = pokemonRepository,
                            pokemonName = it.pokemon.name
                        )
                    },
                    onBack = { backStack.removeLastOrNull() }
                )
            }
        }
    )
}