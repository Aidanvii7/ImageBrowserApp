package com.imagebrowser.feature.images

import com.imagebrowser.feature.images.data.api.GetImageDetailsResponse
import com.imagebrowser.feature.images.data.api.GetImagesResponse

internal fun GetImagesResponse.Image.toImageInfo() = ImageInfo(
    id = id,
    previewUrl = imageUrl,
    previewSize = ImageSize(imageWidth, imageHeight),
    username = username
)

internal fun GetImageDetailsResponse.Image.toImageDetails() = ImageDetails(
    id = id,
    imageUrl = imageUrl,
    imageSize = ImageSize(imageWidth, imageHeight),
    username = username,
    totalViews = totalViews,
    totalLikes = totalLikes,
    totalComments = totalComments,
    totalFavourites = totalFavourites,
    totalDownloads = totalDownloads,
    tags = tags.split(", ")
)