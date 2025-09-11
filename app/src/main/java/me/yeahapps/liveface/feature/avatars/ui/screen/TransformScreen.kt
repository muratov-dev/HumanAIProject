package me.yeahapps.liveface.feature.avatars.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.SubcomposeAsyncImage
import kotlinx.serialization.Serializable
import me.yeahapps.liveface.R
import me.yeahapps.liveface.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.liveface.core.ui.component.button.icons.HumanAIIconButton
import me.yeahapps.liveface.core.ui.component.topbar.HumanAISecondaryTopBar
import me.yeahapps.liveface.core.ui.navigation.commonModifier
import me.yeahapps.liveface.core.ui.theme.HumanAITheme
import me.yeahapps.liveface.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.liveface.core.ui.utils.toPainter
import me.yeahapps.liveface.feature.avatars.domain.ImageStyle
import me.yeahapps.liveface.feature.avatars.ui.action.TransformAction
import me.yeahapps.liveface.feature.avatars.ui.component.ImageStyleCard
import me.yeahapps.liveface.feature.avatars.ui.event.TransformEvent
import me.yeahapps.liveface.feature.avatars.ui.state.TransformState
import me.yeahapps.liveface.feature.avatars.ui.viewmodel.TransformViewModel
import me.yeahapps.liveface.feature.subscription.ui.screen.SubscriptionsContainer

@Serializable
class TransformScreen(val imageUri: String)

@Composable
fun TransformContainer(
    modifier: Modifier = Modifier,
    viewModel: TransformViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToGenerating: (String) -> Unit,
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle { action ->
        when (action) {
            is TransformAction.NavigateToGenerating -> navigateToGenerating(action.avatarUri.toString())
            is TransformAction.NavigateUp -> navigateUp()
            null -> {}
        }
    }
    TransformContent(
        modifier = modifier, state = state, onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun TransformContent(
    modifier: Modifier = Modifier, state: TransformState = TransformState(), onEvent: (TransformEvent) -> Unit = {}
) {
    var isSubscriptionsScreenVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current
    Scaffold(modifier = modifier, topBar = {
        HumanAISecondaryTopBar(
            title = stringResource(R.string.transform_headline),
            navigationIcon = { HumanAIIconButton(icon = R.drawable.ic_arrow_back){
                onEvent(TransformEvent.NavigateUp)
            } },
            actions = {
                state.avatarUrl?.let {
                    HumanAIIconButton(icon = R.drawable.ic_save) {
                        onEvent(TransformEvent.SaveToGallery)
                    }
                }
            })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding(), bottom = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.size(40.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.65f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(32.dp)),
                contentAlignment = Alignment.Center
            ) {
                state.avatarUrl?.let {
                    SubcomposeAsyncImage(
                        model = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                } ?: state.userImageUri?.let {
                    Image(
                        painter = it.toPainter(context),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.matchParentSize()
                    )
                }
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
                            style = style, isSelected = style == state.selectedStyle, onClick = {
                                if (state.hasSubscription) onEvent(TransformEvent.OnStyleSelected(style))
                                else isSubscriptionsScreenVisible = true
                            })
                    }
                }
            }
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.transform_create),
                onClick = { onEvent(TransformEvent.SaveAvatar) },
                enabled = state.canContinue,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }
    }
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

    if (isSubscriptionsScreenVisible) {
        SubscriptionsContainer(
            modifier = Modifier
                .commonModifier()
                .fillMaxSize(),
            onScreenClose = { isSubscriptionsScreenVisible = false })
    }
}