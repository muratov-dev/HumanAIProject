package me.yeahapps.liveface.feature.avatars.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.yeahapps.liveface.feature.avatars.data.repository.AvatarRepositoryImpl
import me.yeahapps.liveface.feature.avatars.domain.repository.AvatarRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AvatarRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAvatarRepository(avatarRepositoryImpl: AvatarRepositoryImpl): AvatarRepository
}