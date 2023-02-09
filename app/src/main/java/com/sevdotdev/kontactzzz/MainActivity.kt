package com.sevdotdev.kontactzzz

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sevdotdev.kontactzzz.contacts.ui.ContactsScreen
import com.sevdotdev.kontactzzz.ui.theme.KontactzzzTheme
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
                    val scaffoldState = rememberScaffoldState()
                    Scaffold(
                        scaffoldState = scaffoldState,
//                        topBar = {
//                            TopAppBar(
//                                contentPadding = PaddingValues(8.dp)
//                            ) {
//                                Text(text = "Kontactzzz", style = MaterialTheme.typography.h4)
//                            }
//                        }
                    ) {
                        ContactsScreen(viewModel = viewModel())
                    }
                }
            }
        }
    }
}