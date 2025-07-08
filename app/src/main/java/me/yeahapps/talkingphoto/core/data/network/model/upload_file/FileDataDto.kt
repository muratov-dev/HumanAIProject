package me.yeahapps.talkingphoto.core.data.network.model.upload_file


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FileDataDto(
    @SerialName("file_path")
    val filePath: String
)