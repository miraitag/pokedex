package com.miraitag.pokedex.ui.screens.home

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.miraitag.pokedex.R
import com.miraitag.pokedex.ui.common.parseTypeToColor
import com.miraitag.pokedex.ui.components.LoadingProgressIndicator
import com.miraitag.pokedex.ui.model.Pokemon
import com.miraitag.pokedex.ui.theme.PokedexTheme
import com.miraitag.pokedex.ui.theme.color.PokemonTheme
import org.koin.androidx.compose.koinViewModel

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
    onPokemonClick: (Pokemon) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    val context: Context = LocalContext.current
    val uiState: HomeUiState by viewModel.uiState.collectAsStateWithLifecycle()

    val homeStateHolder = rememberHomeStateHolder(
        onVoiceResultSuccess = { viewModel.fetchPokemonAndSavePokemonByName(it) }
    )

    LaunchedEffect(uiState.showMessageError) {
        uiState.showMessageError?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onAction(HomeEvents.ShowError(message = it))
        }
    }

    Screen {
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
                    title = { Text("Pokedex") }, scrollBehavior = homeStateHolder.scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(homeStateHolder.scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            if (uiState.isLoading) {
                LoadingProgressIndicator(modifier = Modifier.padding(padding))
            } else {
                if (uiState.pokemons.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            text = "No hay pokemons",
                            color = PokemonTheme.colors.text
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(4.dp),
                        contentPadding = padding
                    ) {
                        items(items = uiState.pokemons, key = { it.id }) { pokemon ->
                            PokemonItem(
                                isFavorite = pokemon.isFavorite,
                                pokemon = pokemon,
                                onClick = { onPokemonClick(pokemon) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PokemonItem(
    isFavorite: Boolean,
    pokemon: Pokemon,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(color = parseTypeToColor(pokemon.type))
            .clickable(onClick = onClick)
    ) {
        Box {
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
            if (isFavorite) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(id = R.string.favorite_description),
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                )
            }
        }
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