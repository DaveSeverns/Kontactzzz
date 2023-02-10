package com.sevdotdev.kontactzzz.activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sevdotdev.kontactzzz.contacts.ui.ContactsScreen
import com.sevdotdev.kontactzzz.core.ui.theme.KontactzzzTheme
import com.sevdotdev.kontactzzz.core.utils.rememberSnackbarHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KontactzzzTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val snackbarHostState = remember {
                        SnackbarHostState()
                    }
                    val snackbarHelper =
                        rememberSnackbarHelper(snackbarHostState = snackbarHostState)
                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState = snackbarHostState) { data ->
                                Snackbar(
                                    snackbarData = data,
                                    backgroundColor = snackbarHelper.snackbarBackgroundColor,
                                    contentColor = snackbarHelper.snackbarContentColor,
                                    actionColor = snackbarHelper.snackbarActionColor
                                )
                            }
                        }
                    ) {
                        MaterialTheme.colors.error
                        ContactsScreen(viewModel = viewModel(), snackbarHelper)
                    }
                }
            }
        }
    }
}