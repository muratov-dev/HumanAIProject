package me.yeahapps.liveface.feature.generating.ui.screen

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import me.yeahapps.liveface.R
import me.yeahapps.liveface.core.ui.component.topbar.HumanAISecondaryTopBar
import me.yeahapps.liveface.core.ui.theme.HumanAITheme
import me.yeahapps.liveface.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.liveface.core.ui.utils.thenIf
import me.yeahapps.liveface.core.ui.utils.thenIfNull
import me.yeahapps.liveface.core.ui.utils.toPainter
import me.yeahapps.liveface.feature.generating.ui.action.CreatingVideoAction
import me.yeahapps.liveface.feature.generating.ui.component.GeneratingSlider
import me.yeahapps.liveface.feature.generating.ui.state.CreatingVideoState
import me.yeahapps.liveface.feature.generating.ui.viewmodel.CreatingVideoViewModel

@Serializable
data class CreatingVideoScreen(val audioScript: String? = null, val audioUri: String? = null, val imageUri: String)

@Composable
fun CreatingVideoContainer(
    modifier: Modifier = Modifier,
    viewModel: CreatingVideoViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToVideo: (Long) -> Unit
) {
    val context = LocalContext.current

    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            is CreatingVideoAction.ShowVideoGeneratingError -> {
                Toast.makeText(context, "UnknownError", Toast.LENGTH_SHORT).show()
                navigateUp()
            }
            is CreatingVideoAction.NavigateToVideo -> navigateToVideo(action.videoId)
            null -> {}
        }
    }
    CreatingVideoContent(modifier = modifier, state = state)
}

@Composable
private fun CreatingVideoContent(modifier: Modifier = Modifier, state: CreatingVideoState = CreatingVideoState()) {
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(animation = tween(2000, easing = LinearEasing))
    )

    Scaffold(
        modifier = modifier,
        topBar = { HumanAISecondaryTopBar(title = stringResource(R.string.creating_video_title)) }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(20.dp))
            state.imageUri?.let {
                Image(
                    painter = it.toPainter(context),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth(0.65f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(32.dp))
                )
            }
            Spacer(Modifier.size(48.dp))
            Text(
                text = stringResource(R.string.creating_video_label),
                color = HumanAITheme.colors.textPrimary,
                style = HumanAITheme.typography.headlineBold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(Modifier.size(8.dp))
            Text(
                text = stringResource(R.string.creating_video_description),
                color = HumanAITheme.colors.textPrimary.copy(alpha = 0.7f),
                style = HumanAITheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_upload_photo),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = stringResource(R.string.creating_video_step_1), modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = ImageVector.vectorResource(if (!state.isImageUploaded) R.drawable.ic_loading else R.drawable.ic_check_circle),
                            tint = HumanAITheme.colors.buttonPrimaryDefault,
                            contentDescription = null,
                            modifier = Modifier
                                .size(32.dp)
                                .thenIf(!state.isImageUploaded) { rotate(angle) })
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .thenIfNull(state.isGeneratingVideo) { alpha(0.5f) }) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_generating),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = stringResource(R.string.creating_video_step_2), modifier = Modifier.weight(1f))
                        state.isGeneratingVideo?.let { isGeneratingVideo ->
                            Icon(
                                imageVector = ImageVector.vectorResource(if (isGeneratingVideo) R.drawable.ic_loading else R.drawable.ic_check_circle),
                                tint = HumanAITheme.colors.buttonPrimaryDefault,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(32.dp)
                                    .thenIf(isGeneratingVideo) { rotate(angle) })
                        }
                    }
                    state.isGeneratingVideo?.let {
                        if (state.isGeneratingVideo) {
                            GeneratingSlider(progress = state.progress, modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .thenIfNull(state.isGeneratingFinished) { alpha(0.5f) }) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_download),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(text = stringResource(R.string.creating_video_step_3), modifier = Modifier.weight(1f))
                        state.isGeneratingFinished?.let { isGeneratingFinished ->
                            Icon(
                                imageVector = ImageVector.vectorResource(if (!isGeneratingFinished) R.drawable.ic_loading else R.drawable.ic_check_circle),
                                tint = HumanAITheme.colors.buttonPrimaryDefault,
                                contentDescription = null,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}