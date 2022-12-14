package com.example.reddittestpetproject.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.reddittestpetproject.repository.RedditRepository
import retrofit2.HttpException
import javax.inject.Inject

class RefreshDataWorker @Inject constructor(
    appContext: Context,
    params: WorkerParameters,
    private val repository: RedditRepository
) : CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            repository.refreshChildren()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}