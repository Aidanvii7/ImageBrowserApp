package com.imagebrowser.app

import android.app.Application

class ImageBrowserApp : Application() {

    private val appDelegate: AppDelegate<ImageBrowserApp> by lazy(LazyThreadSafetyMode.NONE) {
        ImageBrowserAppDelegate(this)
    }

    override fun onCreate() {
        super.onCreate()
        appDelegate.onCreate()
    }
}