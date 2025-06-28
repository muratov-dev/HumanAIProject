package me.yeahapps.myhumanai.features.upload

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.serialization.Serializable

@Serializable
object UploadScreen

@Composable
fun UploadContainer(modifier: Modifier = Modifier) {
    UploadContent(modifier = modifier)
}

@Composable
private fun UploadContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            Text(text = "Upload")
        }
    }
}