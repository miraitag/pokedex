package com.miraitag.pokedex.ui.screens.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraitag.pokedex.data.PokemonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    var state by mutableStateOf(HomeUiState())
        private set

    private val repository = PokemonRepository()

    fun onReadyUI() {
        viewModelScope.launch(Dispatchers.IO) {
            state = state.copy(isLoading = true)
            state = state.copy(
                isLoading = false,
                pokemons = repository.fetchPokemons()
            )
        }
    }
}