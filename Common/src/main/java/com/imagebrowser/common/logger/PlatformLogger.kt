package com.imagebrowser.common.logger

import android.util.Log

class PlatformLogger(
    private val defaultTag: String,
    private val displayThreadName: Boolean = false
) : Logger {
    init {
        if (defaultTag.isBlank()) throw IllegalArgumentException("defaultTag must not be blank")
    }

    override fun d(tag: String?, message: String) {
        Log.d(tag.sanitiseTag(), message.sanitiseMessage())
    }

    override fun e(tag: String?, message: String) {
        Log.e(tag.sanitiseTag(), message.sanitiseMessage())
    }

    private val threadName: String
        get() = Thread.currentThread().name

    private fun String?.sanitiseTag(): String =
        if (isNullOrBlank()) defaultTag else this!!

    private fun String.sanitiseMessage() =
        if (displayThreadName) "$threadName: $this" else this
}