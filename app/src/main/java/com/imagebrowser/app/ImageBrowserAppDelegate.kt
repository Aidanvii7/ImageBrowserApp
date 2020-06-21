package com.imagebrowser.app

import com.imagebrowser.app.databinding.AppDataBindingComponent

import androidx.annotation.VisibleForTesting
import com.imagebrowser.app.di.AppModule
import com.imagebrowser.common.logger.CompositeLogger
import com.imagebrowser.common.logger.PlatformLogger
import com.imagebrowser.feature.images.di.FeatureBrowseImagesCoreImplementationModule
import com.imagebrowser.feature.images.di.FeatureBrowseImagesPresentationModule
import org.koin.android.ext.android.get
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Logger as KoinLogger
import org.koin.core.module.Module as KoinModule
import org.koin.dsl.module

class ImageBrowserAppDelegate(
        override val app: ImageBrowserApp,
        @param:VisibleForTesting
        private val koinLogger: KoinLogger = AndroidLogger(),
        @param:VisibleForTesting
        private val onKoinApplication: KoinApplication.() -> Unit = {},
        @param:VisibleForTesting
        private val koinOverrides: KoinModule.() -> Unit = {}
) : AppDelegate<ImageBrowserApp> {

    override fun onCreate() {
        initLogger()
        initKoin()
        initDataBindingComponent()
    }

    private fun initKoin() {
        startKoin {
            logger(koinLogger)
            androidContext(app.applicationContext)
            modules(modules = modules + overrideModule)
        }.onKoinApplication()
    }

    private fun initDataBindingComponent() {
        app.get<AppDataBindingComponent>().makeDefaultComponent()
    }

    private fun initLogger() {
        CompositeLogger += PlatformLogger(
                defaultTag = "ImageBrowser",
                displayThreadName = true
        )
    }

    private val overrideModule: KoinModule
        get() = module(override = true) { koinOverrides() }

    @VisibleForTesting
    private val modules: List<KoinModule> = listOf(
            AppModule.get(),
            FeatureBrowseImagesPresentationModule.get(),
            FeatureBrowseImagesCoreImplementationModule.get()
    )
}