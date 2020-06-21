@file:Suppress("RemoveExplicitTypeArguments")

package com.imagebrowser.feature.images.presentation.detail

import android.app.Application
import android.content.res.Resources
import androidx.annotation.StringRes
import androidx.lifecycle.AndroidViewModel
import com.imagebrowser.common.CoroutineDispatchers
import com.imagebrowser.feature.images.ImageDetails
import com.imagebrowser.feature.images.ImageInfo
import com.imagebrowser.feature.images.ImageRepository
import com.imagebrowser.feature.images.presentation.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

internal class ImageDetailViewModel(
    application: Application,
    private val imageInfo: ImageInfo,
    private val imageRepository: ImageRepository,
    private val coroutineDispatchers: CoroutineDispatchers
) : AndroidViewModel(application), CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext
        get() = coroutineDispatchers.main + job

    private var cachedImageDetails: ImageDetails? = null

    @ExperimentalCoroutinesApi
    private val imageDetailsFlow: Flow<ImageDetails> = channelFlow<ImageDetails> {
        val imageDetails: ImageDetails? = cachedImageDetails//
        if (imageDetails != null) {
            send(imageDetails)
        } else {
            launch(coroutineDispatchers.io) {
                imageRepository.getImageDetails(imageInfo.id)?.let {
                    cachedImageDetails = it
                    send(it)
                }
            }
        }

    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val imageUrlFlow: Flow<String> = channelFlow {
        send(imageInfo.previewUrl)
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.imageUrl)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val imageAspectRatioFlow: Flow<Float> = channelFlow {
        imageInfo.previewSize.apply {
            send(getAspectRatioFrom(width, height))
        }
        imageDetailsFlow.collectLatest { latestImageDetails ->
            latestImageDetails.imageSize.apply {
                send(getAspectRatioFrom(width, height))
            }
        }
    }

    private fun getAspectRatioFrom(width: Int, height: Int): Float =
        width.toFloat() / height.toFloat()

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val usernameFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.username)
        }
    }.map { username ->
        resources.getString(R.string.username, username)
    }

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val totalViewsFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.totalViews)
        }
    }.formatWithStringResource(R.string.total_views)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val totalLikesFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.totalLikes)
        }
    }.formatWithStringResource(R.string.total_likes)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val totalCommentsFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.totalViews)
        }
    }.formatWithStringResource(R.string.total_comments)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val totalFavouritesFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.totalFavourites)
        }
    }.formatWithStringResource(R.string.total_favourites)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val totalDownloadsFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.totalDownloads)
        }
    }.formatWithStringResource(R.string.total_favourites)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val tagsFlow: Flow<String> = channelFlow {
        imageDetailsFlow.collectLatest { latestImageDetails ->
            send(latestImageDetails.tags)
        }
    }.map { tags ->
        resources.getString(R.string.tags, tags.toString())
    }

    private fun Flow<Int>.formatWithStringResource(@StringRes stringRes: Int): Flow<String> =
        map { resources.getString(stringRes, it) }

    private val resources: Resources
        get() = getApplication<Application>().resources

    private fun <T> Flow<T>.mapToString(): Flow<String> = map { it.toString() }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}