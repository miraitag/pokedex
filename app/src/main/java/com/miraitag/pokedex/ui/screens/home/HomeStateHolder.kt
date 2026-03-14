package com.miraitag.pokedex.ui.screens.home

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.miraitag.pokedex.R
import com.miraitag.pokedex.ui.common.permissionRequestEffect
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
class HomeStateHolder(
    val context: Context,
    val scrollBehavior: TopAppBarScrollBehavior,
    private val onVoiceResultSuccess: (String) -> Unit
) {

    lateinit var speechLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>
    lateinit var requestVoicePermission: () -> Unit

    fun onVoiceResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val spokenText = result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.firstOrNull()
            spokenText?.let {
                showToast(context.getString(R.string.voice_recorder_result, it))
                onVoiceResultSuccess(it)
            }
        }
    }

    fun launchVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(
                RecognizerIntent.EXTRA_PROMPT,
                context.getString(R.string.voice_recorder_init_message)
            )
        }
        speechLauncher.launch(intent)
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberHomeStateHolder(
    context: Context = LocalContext.current,
    topAppBarScrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    onVoiceResultSuccess: (String) -> Unit,
): HomeStateHolder {
    // Usamos remember para que el StateHolder persista durante recomposiciones
    val stateHolder = remember(context, onVoiceResultSuccess) {
        HomeStateHolder(
            context = context,
            scrollBehavior = topAppBarScrollBehavior,
            onVoiceResultSuccess = onVoiceResultSuccess
        )
    }

    // El launcher debe registrarse siempre en la composición
    stateHolder.speechLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = stateHolder::onVoiceResult
    )

    stateHolder.requestVoicePermission = permissionRequestEffect(
        permission = Manifest.permission.RECORD_AUDIO,
        onGranted = { stateHolder.launchVoiceInput() },
        onDenied = {
            stateHolder.showToast(
                context.resources.getString(R.string.voice_recorder_permission_denied),
            )
        }
    )

    return stateHolder
}