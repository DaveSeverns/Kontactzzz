package com.sevdotdev.kontactzzz.core.utils

import android.content.Context
import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.sevdotdev.kontactzzz.R
import com.sevdotdev.kontactzzz.core.domain.model.Error
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * A delegate for the snackbar host state which provides its own internal state to customize the
 * Snackbar with some context whether just showing info or if relaying an error message
 *
 * @property context used to gain access to Android resources
 * @property coroutineScope for performing suspend fuctions associated with displaying Snackbar
 * @property hostState Snackbar host state needed for showing snackbar
 */
class SnackbarHelper(
    private val context: Context,
    private val coroutineScope: CoroutineScope,
    private val hostState: SnackbarHostState? = null,
) {

    private var snackBarState = SnackbarState.Default

    val snackbarBackgroundColor
        @Composable
        get() = when (snackBarState) {
            SnackbarState.Default -> SnackbarDefaults.backgroundColor
            SnackbarState.Error -> MaterialTheme.colors.error
        }
    val snackbarContentColor
        @Composable
        get() = when (snackBarState) {
            SnackbarState.Default -> MaterialTheme.colors.surface
            SnackbarState.Error -> Color.White
        }

    val snackbarActionColor
        @Composable
        get() = when (snackBarState) {
            SnackbarState.Default -> MaterialTheme.colors.primaryVariant
            SnackbarState.Error -> Color.White
        }

    fun showErrorSnackbar(
        error: Error
    ) {
        snackBarState = SnackbarState.Error
        val label = context.getString(R.string.dismiss_label)
        val message = when (error) {
            is Error.NetworkError -> {
                context.getString(R.string.network_error_message)
            }
            else -> {
                context.getString(R.string.unknown_error_message)
            }
        }

        coroutineScope.launch {
            hostState?.showSnackbar(
                message = message,
                actionLabel = label,
                SnackbarDuration.Indefinite
            )
        }
    }
}

/**
 * Provides an remembered value of [SnackbarHelper] to the composition in conveinience function
 */
@Composable
internal fun rememberSnackbarHelper(snackbarHostState: SnackbarHostState? = null): SnackbarHelper {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    return remember {
        SnackbarHelper(
            context = context,
            coroutineScope = scope,
            hostState = snackbarHostState
        )
    }
}

private enum class SnackbarState {
    Default,
    Error
}