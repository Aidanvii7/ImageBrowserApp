package com.imagebrowser.app

import android.app.Application

interface AppDelegate<T : Application> {
    val app: T
    fun onCreate()
}
