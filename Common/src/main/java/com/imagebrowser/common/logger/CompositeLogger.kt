package com.imagebrowser.common.logger

object CompositeLogger : Logger {

    private val loggers = mutableSetOf<Logger>()

    operator fun plusAssign(logger: Logger) {
        loggers.add(logger)
    }

    operator fun minusAssign(logger: Logger) {
        loggers.remove(logger)
    }

    override fun d(tag: String?, message: String) = loggers.forEach { it.d(tag, message) }
    override fun e(tag: String?, message: String) = loggers.forEach { it.e(tag, message) }
}