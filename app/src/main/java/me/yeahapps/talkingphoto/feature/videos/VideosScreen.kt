package me.yeahapps.talkingphoto.feature.videos

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object VideosScreen

@Composable
fun VideosContainer(modifier: Modifier = Modifier) {
    VideosContent(modifier = modifier)
}

@Composable
private fun VideosContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Videos")
        }
    }
}