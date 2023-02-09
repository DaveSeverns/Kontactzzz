package com.sevdotdev.kontactzzz.contacts.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel
) {
    val contactsList by viewModel.contactsViewState.collectAsState()
    val isRefreshing by viewModel.refreshingViewState

    val refreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = viewModel::refreshContacts)
    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.errorSharedFlow.collect{ errorState ->
                errorState?.let {

                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) {
        ContactsScreen(contactsList)
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshState,
            modifier = Modifier.align(
                Alignment.TopCenter
            ),
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContactsScreen(
    contacts: Map<Char,List<Contact>>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        contacts.forEach { (initial, contactsByInitial) ->
            stickyHeader {
                Row(Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    Text(text = initial.toString(), style = MaterialTheme.typography.h5)
                }
            }
            items(contactsByInitial) { contact: Contact ->
                ContactCard(viewState = contact)
            }
        }
    }
}