package com.example.reddittestpetproject.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPost(
    val id: String,
    val title: String,
    val author: String,
    val created_utc: Long,
    val thumbnail: String,
    val num_comments: Int,
    val url_overridden_by_dest: String = "1"
)