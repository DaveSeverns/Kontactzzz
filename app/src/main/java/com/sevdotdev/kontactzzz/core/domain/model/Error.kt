package com.sevdotdev.kontactzzz.core.domain.model

/**
 * Sealed class representing buckets of errors the app may encounter and can
 * easily process.
 */
sealed class Error{
    object NetworkError: Error()
    object UnknownError: Error()
}