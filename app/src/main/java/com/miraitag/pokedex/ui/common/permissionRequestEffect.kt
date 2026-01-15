package com.miraitag.pokedex.ui.common

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
fun permissionRequestEffect(
    permission: String,
    onGranted: () -> Unit,
    onDenied: () -> Unit
): () -> Unit {

    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                onGranted()
            } else {
                onDenied()
            }
        }

    return remember(context, launcher) {
        {
            val isPermissionGranted = ContextCompat.checkSelfPermission(
                context,
                permission
            ) == PackageManager.PERMISSION_GRANTED

            if (isPermissionGranted) {
                onGranted()
            } else {
                launcher.launch(permission)
            }
        }
    }
}