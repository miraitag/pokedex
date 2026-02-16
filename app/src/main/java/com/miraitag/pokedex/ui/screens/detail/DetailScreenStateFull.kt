package com.miraitag.pokedex.ui.screens.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miraitag.pokedex.ui.model.PokemonItem

@Composable
fun DetailScreenStateFull(
    pokemon: PokemonItem,
    onBack: () -> Unit
) {
    val viewModel: DetailViewModel = viewModel()
    val lifecycle = LocalLifecycleOwner.current
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(viewModel, lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is DetailEvents.onFavoriteClicked -> {
                        snackbarHostState.apply {
                            currentSnackbarData?.dismiss()
                            showSnackbar(
                                message = "${event.pokemon.name} agregado a favoritos"
                            )
                        }
                    }
                }
            }
        }
    }

    DetailScreenStateLess(
        pokemon = pokemon,
        snackbarHostState = snackbarHostState,
        viewModel = viewModel,
        onBack = onBack
    )
}