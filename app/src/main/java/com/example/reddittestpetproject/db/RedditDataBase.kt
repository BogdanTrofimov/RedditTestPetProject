package com.example.reddittestpetproject.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataBaseChildren::class, DataBasePostData::class], version = 1)
abstract class RedditDataBase : RoomDatabase() {
    abstract val dao: RedditDao
}