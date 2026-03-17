package com.miraitag.pokedex.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.miraitag.pokedex.R
import com.miraitag.pokedex.ui.common.Heading
import com.miraitag.pokedex.ui.common.parseTypeToColor
import com.miraitag.pokedex.ui.model.Pokemon
import com.miraitag.pokedex.ui.screens.detail.components.Properties
import com.miraitag.pokedex.ui.screens.detail.components.Sprites
import com.miraitag.pokedex.ui.screens.home.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    pokemon: Pokemon,
    viewModel: DetailViewModel,
    onBack: () -> Unit,
) {

    val uiState: DetailUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val isFavoritePokemon: Boolean = uiState.pokemon?.isFavorite ?: false

    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Heading(title = pokemon.name) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(id = R.string.back)
                            )
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { viewModel.onFavoritePokemon() }) {
                    Icon(
                        imageVector = if (isFavoritePokemon) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }
            },
        ) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .memoryCachePolicy(policy = CachePolicy.ENABLED)
                        .diskCachePolicy(policy = CachePolicy.DISABLED)
                        .data(data = pokemon.image)
                        .build(),
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .background(color = parseTypeToColor(type = pokemon.type))
                        .fillMaxWidth()
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Properties(title = "Abilities: ", property = pokemon.abilities)
                    Properties(title = "Forms: ", property = pokemon.forms)
                    Properties(title = "Weight: ", property = pokemon.weight)
                    Sprites(sprites = pokemon.sprites, imageDescription = pokemon.name)
                }
            }
        }
    }
}