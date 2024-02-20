package com.ghitai.petfinder.extensions

import com.ghitai.petfinder.data.network.model.PrimaryPhotoCropped

enum class PhotoExt {
    PHOTO_CROP,
    PHOTO_FULL,
}

fun PrimaryPhotoCropped.getUrl(type: PhotoExt): String {
    return when (type) {
        PhotoExt.PHOTO_CROP -> this.small
        PhotoExt.PHOTO_FULL -> this.full
    }
}