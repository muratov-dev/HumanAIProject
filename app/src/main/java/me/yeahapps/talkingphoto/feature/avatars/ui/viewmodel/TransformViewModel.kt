package me.yeahapps.talkingphoto.feature.avatars.ui.viewmodel

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import me.yeahapps.talkingphoto.core.data.network.utils.toByteArray
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.avatars.domain.repository.AvatarRepository
import me.yeahapps.talkingphoto.feature.avatars.ui.action.TransformAction
import me.yeahapps.talkingphoto.feature.avatars.ui.event.TransformEvent
import me.yeahapps.talkingphoto.feature.avatars.ui.screen.TransformScreen
import me.yeahapps.talkingphoto.feature.avatars.ui.state.TransformState
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class TransformViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
    private val repository: AvatarRepository
) : BaseViewModel<TransformState, TransformEvent, TransformAction>(TransformState()) {

    val args = savedStateHandle.toRoute<TransformScreen>()

    override fun obtainEvent(viewEvent: TransformEvent) {
        when (viewEvent) {
            is TransformEvent.OnStyleSelected -> {
                updateViewState { copy(selectedStyle = viewEvent.style) }
                generateAvatar(viewEvent.style.styleName)
            }
        }
    }

    init {
        updateViewState { copy(userImageUri = args.imageUri.toUri()) }
    }

    fun generateAvatar(stylePrompt: String) = viewModelScoped {
        val imageBytes = currentState.userImageUri?.toByteArray(context) ?: return@viewModelScoped
        val uploadUrl = repository.getUploadUrl(imageBytes) ?: return@viewModelScoped
        Timber.d(uploadUrl.uploadImage)

        repository.uploadImage(currentState.userImageUri!!, uploadUrl.uploadImage).onSuccess {
            val image = repository.generateCartoon(uploadUrl.imageUrl, stylePrompt)
            val result = image?.let { repository.waitForResult(it.orderId) }
            Timber.d(result)
        }.onFailure {
            Timber.e(it)
        }
    }
}