package me.yeahapps.talkingphoto.core.data.network.api

import me.yeahapps.talkingphoto.core.data.network.model.TextToSpeechRequestDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface TextToSpeechApiService {

    @POST
    @Headers("xi-api-key:sk_043fcfa6fe0fadfc54d7431df8d04f5347f6a9e0357be1d9")
    suspend fun textToSpeech(@Url url: String, @Body request: TextToSpeechRequestDto): Response<ResponseBody>
}