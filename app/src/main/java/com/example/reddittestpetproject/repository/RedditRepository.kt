package com.example.reddittestpetproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.reddittestpetproject.db.RedditDao
import com.example.reddittestpetproject.db.asDomainModel
import com.example.reddittestpetproject.domain.Models
import com.example.reddittestpetproject.network.RedditApi
import com.example.reddittestpetproject.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RedditRepository @Inject constructor(
    private val dao: RedditDao,
    private val apiService: RedditApi
) {

    val childrenList: LiveData<List<Models.Children>> =
        Transformations.map(
            dao.getChildren()
        ) {
            it.asDomainModel()
        }

    suspend fun refreshChildren() {
        withContext(Dispatchers.IO) {
            val postResponse = apiService.getPostResponseAsync(null, LIMIT)
            dao.insertChildren(*postResponse.asDatabaseModel())
        }
    }

    companion object {
        private const val LIMIT = 20
    }
}