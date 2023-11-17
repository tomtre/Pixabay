package com.tomtre.pixabay.core.data

import com.tomtre.pixabay.core.model.Image
import com.tomtre.pixabay.core.model.ImageDetails
import com.tomtre.pixabay.core.network.model.NetworkHit

fun NetworkHit.toDomainImageDetails(): ImageDetails =
    ImageDetails(
        id = id,
        largeImageURL = largeImageURL,
        user = user,
        tags = tags,
        likes = likes,
        downloads = downloads,
        comments = comments
    )

fun List<NetworkHit>.toDomainImageDetails(): List<ImageDetails> =
    this.map { it.toDomainImageDetails() }

fun NetworkHit.toDomainImage(): Image =
    Image(
        id = id,
        tags = tags,
        previewURL = previewURL
    )

fun List<NetworkHit>.toDomainImage(): List<Image> =
    this.map { it.toDomainImage() }
