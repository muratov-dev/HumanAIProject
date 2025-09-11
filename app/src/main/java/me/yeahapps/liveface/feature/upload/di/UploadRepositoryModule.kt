package me.yeahapps.liveface.feature.upload.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.yeahapps.liveface.feature.upload.data.repository.UploadRepositoryImpl
import me.yeahapps.liveface.feature.upload.domain.repository.UploadRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UploadRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindUploadRepository(repository: UploadRepositoryImpl): UploadRepository
}
