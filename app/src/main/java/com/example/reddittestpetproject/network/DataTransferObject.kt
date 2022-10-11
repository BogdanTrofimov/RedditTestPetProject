package com.example.reddittestpetproject.network

import com.example.reddittestpetproject.db.DataBaseChildren
import com.example.reddittestpetproject.db.DataBasePostData
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkPostResponse(val data: NetworkData)

fun NetworkPostResponse.asDatabaseModel(): Array<DataBaseChildren> = data.children.map {
    DataBaseChildren(
        childrenID = 0,
        kind = it.kind,
        postData = DataBasePostData(
            id = it.data.id,
            title = it.data.title,
            author = it.data.author,
            created_utc = it.data.created_utc,
            thumbnail = it.data.thumbnail,
            num_comments = it.data.num_comments.toString(),
            url_overridden_by_dest = it.data.url_overridden_by_dest
        )
    )
}.toTypedArray()