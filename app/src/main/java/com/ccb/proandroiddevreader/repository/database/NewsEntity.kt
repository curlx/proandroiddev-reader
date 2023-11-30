package com.ccb.proandroiddevreader.repository.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class NewsEntity(
    @PrimaryKey val guid: String,
    val title: String,
    val link: String,
    val thumbnail: String,
    val author: String,
    val published: Long,
)