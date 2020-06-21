package com.imagebrowser.feature.images.data.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class GetImagesResponse(
    @Json(name = "totalHits")
    val totalImages: Int,
    @Json(name = "hits")
    val images: List<Image>
) {
    @JsonClass(generateAdapter = true)
    class Image(
        val id: Int,
        @Json(name = "previewURL")
        val imageUrl: String,
        @Json(name = "previewWidth")
        val imageWidth: Int,
        @Json(name = "previewHeight")
        val imageHeight: Int,
        @Json(name = "user")
        val username: String
    )
}