package me.yeahapps.myhumanai.features.upload.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.serialization.Serializable
import me.yeahapps.myhumanai.R
import me.yeahapps.myhumanai.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.myhumanai.core.ui.component.topbar.HumanAIPrimaryTopBar
import me.yeahapps.myhumanai.features.upload.ui.component.cards.UploadPhotoCard

@Serializable
object UploadScreen

@Composable
fun UploadContainer(modifier: Modifier = Modifier) {
    UploadContent(modifier = modifier)
}

@Composable
private fun UploadContent(modifier: Modifier = Modifier) {
    Scaffold(modifier = modifier, topBar = {
        HumanAIPrimaryTopBar(title = stringResource(R.string.upload_headline))
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp, start = 16.dp, end = 16.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 16.dp)
            ) {
                item {
                    UploadPhotoCard { }
                }
            }
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.upload_main_button),
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            )
        }
    }
}