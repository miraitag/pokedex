package com.miraitag.pokedex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.miraitag.pokedex.data.PokemonClient
import com.miraitag.pokedex.data.PokemonRepository
import com.miraitag.pokedex.ui.mappers.toUiModel
import com.miraitag.pokedex.ui.model.PokemonItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {
    private val repository = PokemonRepository(PokemonClient.instance)

    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        fetchPokemonsInitial()
    }

    fun fetchPokemonsInitial() {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }
            val pokemons = fetchPokemons()
            _state.update {
                it.copy(
                    isLoading = false,
                    pokemons = pokemons
                )
            }
        }
    }

    fun fetchPokemonByName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update { it.copy(isLoading = true) }

            try {
                val pokemon = repository.fetchPokemonByName(name).toUiModel()
                _state.update { state ->
                    val isPokemonExist = state.pokemons.any { it.name == pokemon.name }
                    val pokemons = if (isPokemonExist) state.pokemons else state.pokemons + pokemon
                    state.copy(
                        isLoading = false,
                        pokemons = pokemons,
                        pokemonToNavigate = pokemon
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        showMessageError = "No se encontro a $name"
                    )
                }
            }
        }
    }

    fun onErrorShown() {
        _state.update { it.copy(showMessageError = null) }
    }

    fun onNavigationHandled() {
        _state.update { it.copy(pokemonToNavigate = null) }
    }

    private suspend fun fetchPokemons(): List<PokemonItem> = withContext(Dispatchers.IO) {
        val rangeIds = 1..(_state.value.limit + 10)
        rangeIds.map { id ->
            async { repository.fetchPokemonByName(id.toString()).toUiModel() }
        }.awaitAll()
    }
}