package me.yeahapps.talkingphoto.core.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.yeahapps.talkingphoto.core.data.database.HumanAIDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideProjectDatabase(@ApplicationContext context: Context): HumanAIDatabase {
        return Room.databaseBuilder(context, HumanAIDatabase::class.java, "humanAI.db")
            .fallbackToDestructiveMigration(false).build()
    }

    @Singleton
    @Provides
    fun provideVideosDao(database: HumanAIDatabase) = database.videosDao()
}