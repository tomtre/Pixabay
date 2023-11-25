package com.tomtre.pixabay.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tomtre.pixabay.core.database.dao.ImagesDao
import com.tomtre.pixabay.core.database.dao.RemoteKeysDao
import com.tomtre.pixabay.core.database.model.ImageEntity
import com.tomtre.pixabay.core.database.model.RemoteKeys

@Database(
    entities = [ImageEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = true
)
abstract class PixabayDatabase : RoomDatabase() {
    abstract fun imagesDao(): ImagesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}
