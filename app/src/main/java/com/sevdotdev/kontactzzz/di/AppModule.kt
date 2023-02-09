package com.sevdotdev.kontactzzz.di

import com.sevdotdev.kontactzzz.core.logger.AndroidLogger
import com.sevdotdev.kontactzzz.core.logger.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesLogger(): Logger = AndroidLogger()
}