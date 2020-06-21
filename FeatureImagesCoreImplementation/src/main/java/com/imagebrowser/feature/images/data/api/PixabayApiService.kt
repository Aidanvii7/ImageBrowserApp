package com.imagebrowser.feature.images.data.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_PATH = "/api"

internal interface PixabayApiService {

    @GET(API_PATH)
    suspend fun getImages(
        @Query("key") key: String,
        @Query("q") searchTerm: String,
        @Query("image_type") imageType: String,
        @Query("per_page") pageSize: Int,
        @Query("page") pageKey: Int
    ): GetImagesResponse

    @GET(API_PATH)
    suspend fun getImageDetails(
        @Query("key") key: String,
        @Query("id") imageId: Int
    ): GetImageDetailsResponse
}