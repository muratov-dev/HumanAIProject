package me.yeahapps.liveface.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import me.yeahapps.liveface.BuildConfig
import me.yeahapps.liveface.core.data.network.api.AvatarApiService
import me.yeahapps.liveface.core.data.network.api.MainApiService
import me.yeahapps.liveface.core.data.network.api.TextToSpeechApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(if (BuildConfig.DEBUG) Level.BODY else Level.NONE)

    @Provides
    @Singleton
    fun provideClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor).connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS).build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, json: Json): Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder().baseUrl("https://dreamfaceapp.com/df-server/").client(httpClient)
            .addConverterFactory(json.asConverterFactory(contentType)).build()
    }

    @Provides
    @Singleton
    fun provideMainApiService(retrofit: Retrofit): MainApiService = retrofit.create(MainApiService::class.java)

    @Provides
    @Singleton
    fun provideAvatarsApiService(retrofit: Retrofit): AvatarApiService = retrofit.create(AvatarApiService::class.java)

    @Provides
    @Singleton
    fun provideTTSApiService(retrofit: Retrofit): TextToSpeechApiService = retrofit.create(TextToSpeechApiService::class.java)
}
