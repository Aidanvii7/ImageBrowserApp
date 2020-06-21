package com.imagebrowser.feature.images.data

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

internal class CacheControlInterceptor(
    private val time: Int,
    private val unit: TimeUnit
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(chain.request())
            .newBuilder()
            .header(
                name = "Cache-Control",
                value = CacheControl.Builder()
                    .maxAge(time, unit)
                    .build()
                    .toString()
            ).build()
}