package com.imagebrowser.app.di

import com.imagebrowser.app.databinding.*
import com.imagebrowser.common.CoroutineDispatchers
import org.koin.dsl.module

object AppModule {

    fun get() = module {
        single {
            AppDataBindingComponent(
                imageViewBindingAdapters = GlideImageViewBindingAdapters
            )
        }
        single { CoroutineDispatchers.DEFAULT }
    }
}
