package me.yeahapps.talkingphoto.feature.videos.ui.viewmodel

import android.content.ContentValues
import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.yeahapps.talkingphoto.core.ui.viewmodel.BaseViewModel
import me.yeahapps.talkingphoto.feature.videos.domain.repository.VideosRepository
import me.yeahapps.talkingphoto.feature.videos.ui.action.VideoInfoAction
import me.yeahapps.talkingphoto.feature.videos.ui.event.VideoInfoEvent
import me.yeahapps.talkingphoto.feature.videos.ui.screen.VideoInfoScreen
import me.yeahapps.talkingphoto.feature.videos.ui.state.VideoInfoState
import timber.log.Timber
import java.io.File
import javax.inject.Inject

@HiltViewModel
class VideoInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    @param:ApplicationContext private val context: Context,
    private val videosRepository: VideosRepository,
) : BaseViewModel<VideoInfoState, VideoInfoEvent, VideoInfoAction>(VideoInfoState()) {

    private val args = savedStateHandle.toRoute<VideoInfoScreen>()

    override fun obtainEvent(viewEvent: VideoInfoEvent) {

    }

    init {
        viewModelScoped {
            val videoInfo = videosRepository.getVideoInfo(args.videoId)
            updateViewState { copy(imageUrl = videoInfo.imageUrl, videoPath = videoInfo.videoPath) }
        }
    }

    suspend fun saveVideoToGallery(path: String, displayName: String): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val sourceFile = File(path)
                if (!sourceFile.exists()) {
                    Timber.e("Файл не найден по пути: $path")
                    return@withContext false
                }

                val resolver = context.contentResolver
                val videoCollection = MediaStore.Video.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                val values = ContentValues().apply {
                    put(MediaStore.Video.Media.DISPLAY_NAME, displayName)
                    put(MediaStore.Video.Media.MIME_TYPE, "video/mp4")
                    put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
                    put(MediaStore.Video.Media.IS_PENDING, 1)
                }

                val uri = resolver.insert(videoCollection, values) ?: return@withContext false

                resolver.openOutputStream(uri)?.use { outputStream ->
                    sourceFile.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                values.clear()
                values.put(MediaStore.Video.Media.IS_PENDING, 0)
                resolver.update(uri, values, null, null)

                true
            } catch (e: Exception) {
                Timber.e(e, "Ошибка при сохранении видео в галерею")
                false
            }
        }
    }
}