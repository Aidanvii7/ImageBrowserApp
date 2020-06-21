package com.imagebrowser.feature.images

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class ImageInfo(
    val id: Int,
    val previewUrl: String,
    val previewSize: ImageSize,
    val username: String
) : Parcelable

@Keep
@Parcelize
data class ImageDetails(
    val id: Int,
    val imageUrl: String,
    val imageSize: ImageSize,
    val username: String,
    val totalViews: Int,
    val totalLikes: Int,
    val totalComments: Int,
    val totalFavourites: Int,
    val totalDownloads: Int,
    val tags: List<String>
) : Parcelable

@Keep
@Parcelize
data class ImageSize(
    val width: Int,
    val height: Int
) : Parcelable