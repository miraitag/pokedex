package com.miraitag.pokedex.ui.screens.detail

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class DetailStateHolder(
    val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope
) {

    fun showFavoriteMessage(pokemonName: String) {
        scope.launch {
            snackbarHostState.apply {
                currentSnackbarData?.dismiss()
                showSnackbar("$pokemonName agregado a favoritos")
            }
        }
    }
}

@Composable
fun rememberDetailStateHolder(
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope()
): DetailStateHolder {
    val stateHolder = remember {
        DetailStateHolder(
            snackbarHostState = snackbarHostState,
            scope = scope
        )
    }

    return stateHolder
}