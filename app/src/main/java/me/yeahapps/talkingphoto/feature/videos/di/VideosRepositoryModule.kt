package me.yeahapps.talkingphoto.feature.videos.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.yeahapps.talkingphoto.feature.videos.data.repository.VideosRepositoryImpl
import me.yeahapps.talkingphoto.feature.videos.domain.repository.VideosRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class VideosRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindVideosRepository(repository: VideosRepositoryImpl): VideosRepository
}
