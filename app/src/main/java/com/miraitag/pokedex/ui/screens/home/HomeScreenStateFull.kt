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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.miraitag.pokedex.R
import com.miraitag.pokedex.ui.common.permissionRequestEffect
import com.miraitag.pokedex.ui.model.PokemonItem
import java.util.Locale

@Composable
fun HomeScreenStateFull(
    onPokemonClick: (PokemonItem) -> Unit
) {
    val viewModel: HomeViewModel = viewModel()
    val context = LocalContext.current
    var searchText by remember { mutableStateOf("") }
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycle = LocalLifecycleOwner.current

    LaunchedEffect(viewModel.uiEvents, lifecycle) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.uiEvents.collect { event ->
                when (event) {
                    is HomeEvents.NavigateToDetail -> {
                        searchText = ""
                        onPokemonClick(event.pokemonItem)
                    }

                    is HomeEvents.ShowError -> {
                        Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }

    val speechLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val spokenText =
                    result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        ?.firstOrNull() ?: ""
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.voice_recorder_result, spokenText),
                    Toast.LENGTH_SHORT
                ).show()
                if (spokenText.isNotEmpty()) {
                    searchText = spokenText
                    viewModel.fetchPokemonByName(spokenText)
                }
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
        onPokemonClick = onPokemonClick,
    )
}