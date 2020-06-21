package com.imagebrowser.feature.images.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GetImageDetailsResponse(
    @Json(name = "hits")
    private val images: List<Image>
) {
    val image: Image?
        get() = images.firstOrNull()

    @JsonClass(generateAdapter = true)
    class Image(
        val id: Int,
        @Json(name = "largeImageURL")
        val imageUrl: String,
        val imageWidth: Int,
        val imageHeight: Int,
        @Json(name = "user")
        val username: String,
        @Json(name = "views")
        val totalViews: Int,
        @Json(name = "likes")
        val totalLikes: Int,
        @Json(name = "comments")
        val totalComments: Int,
        @Json(name = "favorites")
        val totalFavourites: Int,
        @Json(name = "downloads")
        val totalDownloads: Int,
        val tags: String
    )
}