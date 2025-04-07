package com.jetpacktest.data

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreManager(@ApplicationContext context: Context): DataStore{
        return DataStore(context)
    }

    @Provides
    @ViewModelScoped
    fun provideExoplayer(@ApplicationContext context: Context): ExoPlayer = ExoPlayer.Builder(context).build()
}