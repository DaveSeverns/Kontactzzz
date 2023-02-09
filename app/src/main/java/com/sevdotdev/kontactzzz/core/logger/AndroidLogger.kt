package com.sevdotdev.kontactzzz.core.logger

import android.util.Log

class AndroidLogger: Logger {
    override fun debug(tag: String, message: String) {
        Log.d(tag, message)
    }

    override fun error(tag: String, message: String?, throwable: Throwable?) {
        Log.e(tag, message, throwable)
    }
}