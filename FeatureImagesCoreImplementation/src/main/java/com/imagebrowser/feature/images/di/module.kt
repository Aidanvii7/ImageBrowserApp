@file:Suppress("RemoveExplicitTypeArguments")

package com.imagebrowser.feature.images.di

import com.imagebrowser.feature.images.ImageRepository
import com.imagebrowser.feature.images.ImageRepositoryImpl
import com.imagebrowser.feature.images.core.implementation.BuildConfig
import com.imagebrowser.feature.images.data.CacheControlInterceptor
import com.imagebrowser.feature.images.data.api.PixabayApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit.DAYS

object FeatureBrowseImagesCoreImplementationModule {

    fun get() = module {

        factory<MoshiConverterFactory> {
            MoshiConverterFactory.create(Moshi.Builder().add(KotlinJsonAdapterFactory()).build())
        }

        single<OkHttpClient> {
            OkHttpClient
                .Builder()
                .addInterceptor(
                    CacheControlInterceptor(
                        time = 1,
                        unit = DAYS
                    )
                )
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                )
                .build()
        }

        single<PixabayApiService> {
            Retrofit
                .Builder()
                .client(get())
                .baseUrl(BuildConfig.PIXABAY_BASE_URL)
                .addConverterFactory(get<MoshiConverterFactory>())
                .build()
                .create(PixabayApiService::class.java)
        }

        single<ImageRepository> {
            ImageRepositoryImpl(
                service = get(),
                coroutineDispatchers = get()
            )
        }
    }
}
