package com.imagebrowser.common.logger

interface Logger {
    fun d(tag: String? = null, message: String)
    fun e(tag: String? = null, message: String)
}