package com.tomtre.pixabay.core.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tomtre.pixabay.core.database.model.ImageEntity

@Dao
interface ImagesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Query(
        """
           SELECT * FROM image_entities
           WHERE tags LIKE :query
       """
    )
    fun observeImagesByName(query: String): PagingSource<Int, ImageEntity>

    @Query("SELECT * FROM image_entities ORDER BY popularity ASC")
    fun observeImagesByName(): PagingSource<Int, ImageEntity>

    @Query("DELETE FROM image_entities")
    suspend fun clearImages()
}
