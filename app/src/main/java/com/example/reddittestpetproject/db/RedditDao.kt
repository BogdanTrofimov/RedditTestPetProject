package com.example.reddittestpetproject.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RedditDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChildren(vararg children: DataBaseChildren)

    @Query("SELECT * FROM databasechildren")
    fun getChildren(): LiveData<List<DataBaseChildren>>
}