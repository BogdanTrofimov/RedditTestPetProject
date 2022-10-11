package com.example.reddittestpetproject.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


private lateinit var INSTANCE: RedditDataBase

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    fun provideDataBase(@ApplicationContext context: Context)
            : RedditDataBase {
        synchronized(RedditDataBase::class.java) {
            if (!::INSTANCE.isInitialized) {
                INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    RedditDataBase::class.java, "reddit"
                ).build()
            }
        }
        return INSTANCE
    }

    @Provides
    fun provideDao(db: RedditDataBase): RedditDao = db.dao

}