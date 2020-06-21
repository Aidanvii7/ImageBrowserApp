package com.imagebrowser.common.logger

fun logD(tag: String? = null, message: () -> String) = CompositeLogger.d(tag, message())
fun logE(tag: String? = null, message: () -> String) = CompositeLogger.e(tag, message())
