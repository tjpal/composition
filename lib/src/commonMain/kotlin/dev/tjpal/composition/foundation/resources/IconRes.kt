package dev.tjpal.composition.foundation.resources

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun IconRes(resource: DrawableResource, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(resource),
        contentDescription = null,
        modifier = modifier
    )
}