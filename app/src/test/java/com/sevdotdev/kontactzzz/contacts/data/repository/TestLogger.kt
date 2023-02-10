package com.sevdotdev.kontactzzz.contacts.data.repository

import com.sevdotdev.kontactzzz.core.logger.Logger

object TestLogger: Logger {
    override fun debug(tag: String, message: String) {
        /*NOOP*/
    }

    override fun error(tag: String, message: String?, throwable: Throwable?) {
        /*NOOP*/
    }
}