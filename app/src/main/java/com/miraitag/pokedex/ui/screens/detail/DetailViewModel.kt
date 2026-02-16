package com.miraitag.pokedex.ui.screens.detail

import androidx.lifecycle.ViewModel
import com.miraitag.pokedex.ui.model.PokemonItem
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class DetailViewModel : ViewModel() {

    private val _uiEvents = Channel<DetailEvents>()
    val uiEvents = _uiEvents.receiveAsFlow()

    fun onFavoriteClicked(pokemon: PokemonItem) {
        _uiEvents.trySend(DetailEvents.onFavoriteClicked(pokemon))
    }
}
