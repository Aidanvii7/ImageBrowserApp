package com.imagebrowser.feature.images

import androidx.paging.PagingSource
import com.imagebrowser.feature.images.core.implementation.BuildConfig
import com.imagebrowser.feature.images.data.api.GetImagesResponse
import com.imagebrowser.feature.images.data.api.PixabayApiService
import retrofit2.HttpException
import java.io.IOException

internal class ImageDataSource(
    private val service: PixabayApiService
) : PagingSource<Int, ImageInfo>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageInfo> =
        try {
            val pageKey = params.key ?: 1
            val response = getImages(params, pageKey)
            val previousKey = getPreviousKey(pageKey)
            val nextKey = getNextKey(response, params, pageKey)
            buildPageFrom(response, previousKey, nextKey)
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    private suspend fun getImages(
        params: LoadParams<Int>,
        pageKey: Int
    ): GetImagesResponse = service.getImages(
        key = BuildConfig.PIXABAY_API_KEY,
        searchTerm = BuildConfig.PIXABAY_SEARCH_TERM,
        imageType = "photo",
        pageSize = params.loadSize,
        pageKey = pageKey
    )

    private fun getPreviousKey(pageKey: Int): Int? =
        if (pageKey == 1) null else pageKey + 1

    private fun getNextKey(
        response: GetImagesResponse,
        params: LoadParams<Int>,
        pageKey: Int
    ): Int? {
        val pagesRemaining = (response.totalImages / params.loadSize) - pageKey
        return if (pagesRemaining == 0) null else pageKey + 1
    }

    private fun buildPageFrom(
        response: GetImagesResponse,
        previousKey: Int?,
        nextKey: Int?
    ) = LoadResult.Page(
        data = response.images.map { it.toImageInfo() },
        prevKey = previousKey,
        nextKey = nextKey
    )
}