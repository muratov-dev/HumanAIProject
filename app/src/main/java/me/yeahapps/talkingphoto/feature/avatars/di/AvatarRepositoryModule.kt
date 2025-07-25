package me.yeahapps.talkingphoto.feature.avatars.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.yeahapps.talkingphoto.feature.avatars.data.repository.AvatarRepositoryImpl
import me.yeahapps.talkingphoto.feature.avatars.domain.repository.AvatarRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AvatarRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAvatarRepository(avatarRepositoryImpl: AvatarRepositoryImpl): AvatarRepository
}