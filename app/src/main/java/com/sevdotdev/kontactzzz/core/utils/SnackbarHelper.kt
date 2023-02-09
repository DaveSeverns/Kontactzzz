package com.sevdotdev.kontactzzz.core.utils

import android.content.Context
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import com.sevdotdev.kontactzzz.R
import com.sevdotdev.kontactzzz.core.domain.model.Error

class SnackbarHelper(
    private val context: Context,
    private val hostState: SnackbarHostState
) {
    suspend fun showErrorSnackbar(
        error: Error
    ) {
        val label = context.getString(R.string.dismiss_label)
        val message = when (error) {
            is Error.NetworkError -> {
                context.getString(R.string.network_error_message)
            }
            else -> {
                context.getString(R.string.unknown_error_message)
            }
        }

        hostState.showSnackbar(
            message = message,
            actionLabel = label,
            SnackbarDuration.Long
        )
    }
}