package com.miraitag.pokedex.ui.screens.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.miraitag.pokedex.Pokemon
import com.miraitag.pokedex.R
import com.miraitag.pokedex.pokemons
import com.miraitag.pokedex.ui.common.permissionRequestEffect
import com.miraitag.pokedex.ui.theme.PokedexTheme
import java.util.Locale

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
fun HomeScreen(onPokemonClick: (Pokemon) -> Unit) {
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }


    val speechLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val result = result.data
                val resultList = result?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                val spokenText = resultList?.first() ?: ""
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.voice_recorder_result, spokenText),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    fun launchVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            .putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            .putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            .putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                context.resources.getString(R.string.voice_recorder_init_message)
            )
        speechLauncher.launch(intent)
    }

    val requestVoicePermission = permissionRequestEffect(
        permission = Manifest.permission.RECORD_AUDIO,
        onGranted = {
            launchVoiceInput()
        },
        onDenied = {
            Toast.makeText(
                context,
                context.resources.getString(R.string.voice_recorder_permission_denied),
                Toast.LENGTH_LONG
            ).show()
        }
    )

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        requestVoicePermission()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = stringResource(id = R.string.voice_recorder_search)
                    )
                }
            },
            topBar = {
                TopAppBar(
                    title = { Text("Pokedex") },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            contentWindowInsets = WindowInsets.safeDrawing
        ) { padding ->
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(4.dp),
                contentPadding = padding
            ) {
                items(pokemons) {
                    PokemonItem(pokemon = it, onClick = { onPokemonClick(it) })
                }
            }
        }
    }
}

@Composable
fun PokemonItem(pokemon: Pokemon, onClick: () -> Unit) {
    Column(modifier = Modifier.clickable(onClick = onClick)) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .memoryCachePolicy(policy = CachePolicy.ENABLED)
                .diskCachePolicy(policy = CachePolicy.DISABLED)
                .data(pokemon.image)
                .build(),
            contentDescription = pokemon.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.small)
        )
        Text(
            text = pokemon.name,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(8.dp)
        )
    }
}