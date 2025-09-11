package me.yeahapps.liveface.feature.avatars.ui.viewmodel

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import me.yeahapps.liveface.core.data.BillingManager
import me.yeahapps.liveface.core.data.network.utils.toByteArray
import me.yeahapps.liveface.core.ui.viewmodel.BaseViewModel
import me.yeahapps.liveface.feature.avatars.domain.repository.AvatarRepository
import me.yeahapps.liveface.feature.avatars.ui.action.TransformAction
import me.yeahapps.liveface.feature.avatars.ui.event.TransformEvent
import me.yeahapps.liveface.feature.avatars.ui.screen.TransformScreen
import me.yeahapps.liveface.feature.avatars.ui.state.TransformState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransformViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
    private val repository: AvatarRepository,
    private val billingManager: BillingManager
) : BaseViewModel<TransformState, TransformEvent, TransformAction>(TransformState()) {

    val args = savedStateHandle.toRoute<TransformScreen>()

    override fun obtainEvent(viewEvent: TransformEvent) {
        when (viewEvent) {
            is TransformEvent.OnStyleSelected -> {
                updateViewState { copy(selectedStyle = viewEvent.style) }
                generateAvatar(viewEvent.style.propmt)
            }

            TransformEvent.NavigateUp -> sendAction(TransformAction.NavigateUp)
            TransformEvent.SaveAvatar -> saveAvatar()
            TransformEvent.SaveToGallery -> viewModelScoped { repository.saveAvatarToGallery() }
        }
    }

    init {
        updateViewState { copy(userImageUri = args.imageUri.toUri()) }
        viewModelScoped {
            billingManager.isSubscribed.collectLatest {
                updateViewState { copy(hasSubscription = it) }
            }
        }
    }

    fun generateAvatar(stylePrompt: String) = viewModelScoped {
        updateViewState { copy(isLoading = true) }
        val imageBytes = currentState.userImageUri?.toByteArray(context) ?: return@viewModelScoped
        val uploadUrl = repository.getUploadUrl(imageBytes) ?: return@viewModelScoped
        Timber.d(uploadUrl.uploadImage)

        repository.uploadImage(currentState.userImageUri!!, uploadUrl.uploadImage).onSuccess {
            val image = repository.generateCartoon(uploadUrl.imageUrl, stylePrompt)
            val result = image?.let { repository.waitForResult(it.orderId) }
            Timber.d(result)
            updateViewState { copy(canContinue = true, avatarUrl = result) }
        }.onFailure {
            Timber.e(it)
        }
        updateViewState { copy(isLoading = false) }
    }

    private fun saveAvatar() = viewModelScoped {
        updateViewState { copy() }
        val avatarUri = repository.saveAvatar(currentState.avatarUrl)
        avatarUri?.let { sendAction(TransformAction.NavigateToGenerating(it)) }
    }
}