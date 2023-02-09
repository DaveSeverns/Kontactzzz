package com.sevdotdev.kontactzzz.core.domain.model

import androidx.compose.runtime.Stable

@Stable
sealed class Error{
    object NetworkError: Error()
    object UnknownError: Error()
}