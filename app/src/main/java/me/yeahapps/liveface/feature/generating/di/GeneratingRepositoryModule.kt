package me.yeahapps.liveface.feature.generating.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.yeahapps.liveface.feature.generating.data.repository.GeneratingRepositoryImpl
import me.yeahapps.liveface.feature.generating.domain.repository.GeneratingRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class GeneratingRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindGeneratingRepository(repository: GeneratingRepositoryImpl): GeneratingRepository
}
