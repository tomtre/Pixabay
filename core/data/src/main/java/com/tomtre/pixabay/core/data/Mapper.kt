package com.tomtre.pixabay.core.data

import com.tomtre.pixabay.core.database.model.ImageEntity
import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.network.model.NetworkHit

fun NetworkHit.toEntity(popularity: Int): ImageEntity =
    ImageEntity(
        id = id,
        previewURL = previewURL,
        largeImageURL = largeImageURL,
        user = user,
        tags = tags,
        likes = likes,
        downloads = downloads,
        comments = comments,
        popularity = popularity
    )

fun ImageEntity.toDomain(): Image =
    Image(
        id = id,
        userName = user,
        tags = tags,
        previewURL = previewURL
    )

fun ImageEntity.toDomainImageDetails(): ImageDetails =
    ImageDetails(
        id = id,
        largeImageURL = largeImageURL,
        user = user,
        tags = tags,
        likes = likes,
        downloads = downloads,
        comments = comments
    )
