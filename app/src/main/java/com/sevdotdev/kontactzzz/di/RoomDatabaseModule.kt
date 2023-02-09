package com.sevdotdev.kontactzzz.di

import android.content.Context
import androidx.room.Room
import com.sevdotdev.kontactzzz.contacts.data.dao.ContactDao
import com.sevdotdev.kontactzzz.core.data.database.KontactzzzAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
private object RoomDatabaseModule {

    @Provides
    fun providesContactDao(kontactzzzAppDatabase: KontactzzzAppDatabase): ContactDao =
        kontactzzzAppDatabase.contactDao()

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext appContext: Context): KontactzzzAppDatabase =
        Room.databaseBuilder(
            context = appContext,
            klass = KontactzzzAppDatabase::class.java,
            name = "KontactzzzDB"
        ).build()


}