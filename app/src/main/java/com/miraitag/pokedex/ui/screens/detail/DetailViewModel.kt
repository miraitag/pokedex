package com.miraitag.pokedex.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.miraitag.pokedex.ui.model.PokemonItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DetailViewModel : ViewModel() {

    private val _state = MutableStateFlow(DetailUiState())
    val state = _state.asStateFlow()

    fun addFavoritePokemon(pokemon: PokemonItem) {
        _state.update {
            it.copy(
                favoritePokemon = pokemon
            )
        }
    }
}
