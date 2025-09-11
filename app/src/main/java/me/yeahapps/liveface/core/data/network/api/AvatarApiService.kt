package me.yeahapps.liveface.core.data.network.api

import me.yeahapps.liveface.feature.avatars.data.model.OrderStatusResponseDto
import me.yeahapps.liveface.feature.avatars.data.model.UploadAvatarRequestDto
import me.yeahapps.liveface.feature.avatars.data.model.UploadAvatarResponseDto
import me.yeahapps.liveface.feature.avatars.data.model.UploadUrlRequestDto
import me.yeahapps.liveface.feature.avatars.data.model.UploadUrlResponseDto
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.HeaderMap
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface AvatarApiService {

    @POST
    @Headers(
        "x-api-key:a08d68814db245db8d1baf626b5d20c4_d6c66440cdef41669f4540439d8270ce_andoraitools",
        "Content-Type:application/json"
    )
    suspend fun getUploadUrl(@Url url: String, @Body requestData: UploadUrlRequestDto): Response<UploadUrlResponseDto>

    @PUT
    @Headers(
        "x-api-key:a08d68814db245db8d1baf626b5d20c4_d6c66440cdef41669f4540439d8270ce_andoraitools",
        "Content-Type:image/jpeg")
    suspend fun uploadImage(
        @HeaderMap headers: Map<String, String>, @Url url: String, @Body requestData: RequestBody
    ): Response<Unit>

    @POST
    @Headers(
        "x-api-key:a08d68814db245db8d1baf626b5d20c4_d6c66440cdef41669f4540439d8270ce_andoraitools",
        "Content-Type:application/json"
    )
    suspend fun generateCartoon(
        @Url url: String, @Body requestData: UploadAvatarRequestDto
    ): Response<UploadAvatarResponseDto>

    @POST
    @Headers(
        "x-api-key:a08d68814db245db8d1baf626b5d20c4_d6c66440cdef41669f4540439d8270ce_andoraitools",
        "Content-Type:application/json"
    )
    suspend fun checkOrderStatus(@Url url: String, @Body requestData: Map<String, String>): Response<OrderStatusResponseDto>
}