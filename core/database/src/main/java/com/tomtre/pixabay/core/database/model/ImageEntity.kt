package com.tomtre.pixabay.core.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "image_entities")
data class ImageEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo("preview_url")
    val previewURL: String,
    @ColumnInfo(name = "large_image_url")
    val largeImageURL: String,
    @ColumnInfo(name = "user")
    val user: String,
    @ColumnInfo(name = "tags")
    val tags: String,
    @ColumnInfo(name = "likes")
    val likes: Int,
    @ColumnInfo(name = "downloads")
    val downloads: Int,
    @ColumnInfo(name = "comments")
    val comments: Int,
    @ColumnInfo(name = "popularity")
    val popularity: Int
)
