package me.yeahapps.myhumanai.features.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object SettingsScreen

@Composable
fun SettingsContainer(modifier: Modifier = Modifier) {
    SettingsContent(modifier = modifier)
}

@Composable
private fun SettingsContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            Text(text = "Settings")
        }
    }
}