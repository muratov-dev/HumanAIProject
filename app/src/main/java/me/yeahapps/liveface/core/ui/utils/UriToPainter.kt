package me.yeahapps.liveface.core.ui.utils

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest

@Composable
fun Uri.toPainter(context: Context): Painter =
    rememberAsyncImagePainter(ImageRequest.Builder(context).data(this).build())