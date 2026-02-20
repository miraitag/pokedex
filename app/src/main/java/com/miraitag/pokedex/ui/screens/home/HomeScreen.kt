package com.miraitag.pokedex.ui.screens.home

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.miraitag.pokedex.R
import com.miraitag.pokedex.ui.common.parseTypeToColor
import com.miraitag.pokedex.ui.components.LoadingProgressIndicator
import com.miraitag.pokedex.ui.model.PokemonItem
import com.miraitag.pokedex.ui.theme.PokedexTheme

@Composable
fun Screen(content: @Composable () -> Unit) {
    PokedexTheme {
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.background,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onPokemonClick: (PokemonItem) -> Unit,
) {
    val viewModel: HomeViewModel = viewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val homeStateHolder = rememberHomeStateHolder(
        onVoiceResultSuccess = { viewModel.fetchPokemonByName(it) }
    )

    LaunchedEffect(state.pokemonToNavigate) {
        state.pokemonToNavigate?.let { pokemon ->
            onPokemonClick(pokemon)
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(state.showMessageError) {
        state.showMessageError?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onErrorShown()
        }
    }

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = homeStateHolder.requestVoicePermission) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = stringResource(id = R.string.voice_recorder_search)
                    )
                }
            },
            topBar = {
                TopAppBar(
                    title = { Text("Pokedex") }, scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            if (state.isLoading) {
                LoadingProgressIndicator(modifier = Modifier.padding(padding))
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.padding(4.dp),
                    contentPadding = padding
                ) {
                    items(items = state.pokemons, key = { it.id }) { pokemon ->
                        PokemonItem(
                            pokemon = pokemon,
                            onClick = { onPokemonClick(pokemon) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(
    pokemon: PokemonItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = parseTypeToColor(pokemon.type))
            .clickable(onClick = onClick)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.image)
                .crossfade(true)
                .memoryCachePolicy(policy = CachePolicy.ENABLED)
                .diskCachePolicy(policy = CachePolicy.DISABLED)
                .build(),
            contentDescription = pokemon.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            modifier = Modifier
                .padding(horizontal = 2.dp)
                .padding(vertical = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = pokemon.name,
            style = MaterialTheme.typography.headlineSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}