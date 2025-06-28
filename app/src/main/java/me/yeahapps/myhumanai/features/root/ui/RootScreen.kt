package me.yeahapps.myhumanai.features.root.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object RootScreen

@Composable
fun RootContainer(modifier: Modifier = Modifier) {
    RootContent(modifier = modifier)
}

@Composable
private fun RootContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
    }
}