package com.miraitag.pokedex.ui.screens.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.miraitag.pokedex.ui.common.Heading

@Composable
fun Sprites(
    sprites: List<String?>,
    imageDescription: String,
    modifier: Modifier = Modifier
) {
    Heading(title = "Sprites")
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        sprites.forEach { sprite ->
            AsyncImage(
                modifier = Modifier.size(200.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .memoryCachePolicy(policy = CachePolicy.ENABLED)
                    .diskCachePolicy(policy = CachePolicy.DISABLED)
                    .data(data = sprite)
                    .build(),
                contentDescription = imageDescription,
                contentScale = ContentScale.Crop
            )
        }
    }
}