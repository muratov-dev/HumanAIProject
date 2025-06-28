package me.yeahapps.myhumanai.features.avatars

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object AvatarsScreen

@Composable
fun AvatarsContainer(modifier: Modifier = Modifier) {
    AvatarsContent(modifier = modifier)
}

@Composable
private fun AvatarsContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            Text(text = "Avatars")
        }
    }
}