@file:JvmName("RedditDataBaseKt")

package com.example.reddittestpetproject.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reddittestpetproject.domain.Models
import com.example.reddittestpetproject.util.toUTCDate

@Entity
data class DataBaseChildren constructor(
    @PrimaryKey(autoGenerate = true)
    var childrenID: Long,
    val kind: String,
    @Embedded    //Embedded подскажет Room, что надо просто взять поля из DatabasePostData и считать их полями таблицы DatabaseChildren.
    val postData: DataBasePostData
)

@Entity
data class DataBasePostData constructor(
    @PrimaryKey
    val id: String,
    val title: String,
    val author: String,
    val created_utc: Long,
    val thumbnail: String,
    val num_comments: String,
    val url_overridden_by_dest: String,
)

fun List<DataBaseChildren>.asDomainModel(): List<Models.Children> = map {
    Models.Children(
        kind = it.kind,
        postData = Models.PostData(
            id = it.postData.id,
            title = it.postData.title,
            author = it.postData.author,
            created_utc = it.postData.created_utc.toUTCDate(),
            thumbnail = it.postData.thumbnail,
            num_comments = it.postData.num_comments,
            url_overridden_by_dest = it.postData.url_overridden_by_dest
        )
    )
}