package me.yeahapps.liveface.feature.onboarding.ui.screen

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.fromHtml
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.serialization.Serializable
import me.yeahapps.liveface.R
import me.yeahapps.liveface.core.ui.component.RequestInAppReview
import me.yeahapps.liveface.core.ui.component.button.filled.HumanAIPrimaryButton
import me.yeahapps.liveface.core.ui.theme.HumanAITheme
import me.yeahapps.liveface.core.ui.utils.collectFlowWithLifecycle
import me.yeahapps.liveface.feature.onboarding.ui.action.OnboardingAction
import me.yeahapps.liveface.feature.onboarding.ui.event.OnboardingEvent
import me.yeahapps.liveface.feature.onboarding.ui.state.OnboardingState
import me.yeahapps.liveface.feature.onboarding.ui.viewmodel.OnboardingViewModel

@Serializable
object OnboardingScreen

@Composable
fun OnboardingContainer(
    modifier: Modifier = Modifier,
    viewModel: OnboardingViewModel = hiltViewModel(),
    navigateToSubscriptions: () -> Unit = {}
) {
    val activity = LocalActivity.current
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    viewModel.viewActions.collectFlowWithLifecycle(viewModel) { action ->
        when (action) {
            OnboardingAction.CloseApp -> activity?.finish()
            OnboardingAction.NavigateToSubscriptionScreen -> navigateToSubscriptions()
            null -> {}
        }
    }
    OnboardingContent(
        modifier = modifier, state = state, onEvent = remember { { event -> viewModel.obtainEvent(event) } })
}

@Composable
private fun OnboardingContent(
    modifier: Modifier = Modifier, state: OnboardingState = OnboardingState(), onEvent: (OnboardingEvent) -> Unit = {}
) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(state.slideIndex) {
        if (state.slideIndex == 1) showDialog = true
    }

    BackHandler {
        onEvent(OnboardingEvent.ShowPreviousSlide)
    }


    Box(modifier = modifier.navigationBarsPadding()) {
        Image(
            painter = painterResource(
                when (state.slideIndex) {
                    0 -> R.drawable.im_onboarding_1
                    1 -> R.drawable.im_onboarding_2
                    else -> R.drawable.im_onboarding_1
                }
            ), contentScale = ContentScale.FillWidth, contentDescription = null, modifier = Modifier.fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_onboarding_decoration), contentDescription = null
            )
            Spacer(Modifier.size(24.dp))
            Text(
                style = HumanAITheme.typography.titleBold,
                color = HumanAITheme.colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = when (state.slideIndex) {
                    0 -> stringResource(R.string.onboarding_title_1)
                    1 -> stringResource(R.string.onboarding_title_2)
                    else -> ""
                }
            )
            Spacer(Modifier.size(16.dp))
            Text(
                style = HumanAITheme.typography.bodyRegular,
                color = HumanAITheme.colors.textPrimary,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .fillMaxWidth(),
                text = when (state.slideIndex) {
                    0 -> stringResource(R.string.onboarding_description_1)
                    1 -> stringResource(R.string.onboarding_description_2)
                    else -> ""
                }
            )
            Spacer(Modifier.size(32.dp))
            HumanAIPrimaryButton(
                centerContent = stringResource(R.string.common_next),
                onClick = { onEvent(OnboardingEvent.ShowNextSlide) },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            if (state.slideIndex == 1) {
                Spacer(Modifier.size(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompositionLocalProvider(
                        LocalContentColor provides HumanAITheme.colors.buttonTextSecondary,
                        LocalTextStyle provides HumanAITheme.typography.bodyRegular.copy(fontSize = 12.sp)
                    ) {
                        val policyText =
                            AnnotatedString.fromHtml(htmlString = stringResource(R.string.common_privacy_policy))
                        val termsText =
                            AnnotatedString.fromHtml(htmlString = stringResource(R.string.common_terms_of_use))
                        Text(text = policyText)
                        Text(text = "|")
                        Text(text = termsText)
                    }
                }
            }
            Spacer(Modifier.size(if (state.slideIndex == 1) 20.dp else 40.dp))
        }
    }

    RequestInAppReview(showDialog, onDismiss = { showDialog = false }, context = LocalContext.current)
}