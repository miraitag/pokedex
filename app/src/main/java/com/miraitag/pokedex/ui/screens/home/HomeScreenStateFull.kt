package com.miraitag.pokedex.ui.screens.home

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miraitag.pokedex.R
import com.miraitag.pokedex.ui.common.permissionRequestEffect
import com.miraitag.pokedex.ui.model.PokemonItem
import java.util.Locale

@Composable
fun HomeScreenStateFull(
    onNavigateToDetailPokemon: (PokemonItem) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.pokemonToNavigate) {
        state.pokemonToNavigate?.let { pokemon ->
            onNavigateToDetailPokemon(pokemon)
            viewModel.onNavigationHandled()
        }
    }

    LaunchedEffect(state.showMessageError) {
        state.showMessageError?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            viewModel.onErrorShown()
        }
    }

    val speechLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.voice_recorder_result, spokenText),
                    Toast.LENGTH_SHORT
                ).show()
                spokenText?.let { viewModel.fetchPokemonByName(it) }
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

    val requestVoicePermission =
        permissionRequestEffect(
            permission = Manifest.permission.RECORD_AUDIO,
            onGranted = { launchVoiceInput() },
            onDenied = {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.voice_recorder_permission_denied),
                    Toast.LENGTH_LONG
                ).show()
            }
        )

    HomeScreenStateLess(
        state = state,
        onVoiceSearch = requestVoicePermission,
        onNavigateToDetailPokemon = onNavigateToDetailPokemon,
    )
}