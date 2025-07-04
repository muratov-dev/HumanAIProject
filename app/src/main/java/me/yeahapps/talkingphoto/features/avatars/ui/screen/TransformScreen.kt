package me.yeahapps.talkingphoto.features.avatars.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import me.yeahapps.talkingphoto.R
import me.yeahapps.talkingphoto.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.talkingphoto.core.ui.component.button.icons.HumanAIIconButton
import me.yeahapps.talkingphoto.core.ui.component.topbar.HumanAISecondaryTopBar
import me.yeahapps.talkingphoto.core.ui.theme.HumanAITheme
import me.yeahapps.talkingphoto.core.ui.utils.toPainter
import me.yeahapps.talkingphoto.features.avatars.domain.ImageStyle
import me.yeahapps.talkingphoto.features.avatars.ui.component.ImageStyleCard
import me.yeahapps.talkingphoto.features.avatars.ui.event.TransformEvent
import me.yeahapps.talkingphoto.features.avatars.ui.state.TransformState
import me.yeahapps.talkingphoto.features.avatars.ui.viewmodel.TransformViewModel

@Serializable
class TransformScreen(val imageUri: String)

@Composable
fun TransformContainer(modifier: Modifier = Modifier, viewModel: TransformViewModel = hiltViewModel()) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    TransformContent(
        modifier = modifier, state = state, onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun TransformContent(
    modifier: Modifier = Modifier, state: TransformState = TransformState(), onEvent: (TransformEvent) -> Unit = {}
) {
    val context = LocalContext.current
    Scaffold(modifier = modifier, topBar = {
        HumanAISecondaryTopBar(
            title = stringResource(R.string.transform_headline),
            navigationIcon = { HumanAIIconButton(icon = R.drawable.ic_arrow_back) })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(40.dp))
            state.userImageUri?.let {
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
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(24.dp, alignment = Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.transform_select_style),
                    style = HumanAITheme.typography.headlineExtraBold,
                    color = HumanAITheme.colors.textPrimary,
                    textAlign = TextAlign.Center
                )
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(ImageStyle.entries) { style ->
                        ImageStyleCard(
                            style = style,
                            isSelected = style == state.selectedStyle,
                            onClick = { onEvent(TransformEvent.OnStyleSelected(style)) })
                    }
                }
            }
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.transform_create),
                onClick = {},
                enabled = state.canContinue,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
}