package com.sevdotdev.kontactzzz.contacts.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.sevdotdev.kontactzzz.R
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import com.sevdotdev.kontactzzz.core.utils.SnackbarHelper
import kotlinx.coroutines.launch

/**
 * This function is responsible for exposing the rest of the
 * [com.sevdotdev.kontactzzz.contacts.ui] components to the rest of the application
 *
 * @param viewModel a [ContactsViewModel] which provides the necessary state for the Composable
 * functions to use
 * @param snackbarHelper a [SnackbarHelper] works as a delegate to the [SnackbarHost] which
 * is part of the [Scaffold] in the main activity, the help allows this screen to propagte messages
 * to be displayed in the Snackbar
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContactsScreen(
    viewModel: ContactsViewModel,
    snackbarHelper: SnackbarHelper,
) {
    val contactsViewState by viewModel.contactsViewState.collectAsState()
    val isRefreshing by viewModel.refreshingViewState

    val refreshState =
        rememberPullRefreshState(refreshing = isRefreshing, onRefresh = viewModel::refreshContacts)
    LaunchedEffect(key1 = Unit) {
        launch {
            viewModel.errorSharedFlow.collect { errorState ->
                errorState?.let { error ->
                    snackbarHelper.showErrorSnackbar(error)
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(refreshState)
    ) {
        ContactsScreen(contacts = contactsViewState)
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshState,
            modifier = Modifier.align(
                Alignment.TopCenter
            ),
        )
    }
}

/**
 * private composbale which takes in
 * @param contacts and is responsible for composing the main layout of the screen,
 * in the event [contacts] is empty, it will display a message in the middle of the screen
 * otherwise it will display the contacts in a list, which is grouped by the first letter in
 * the contact's name
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContactsScreen(
    contacts: Map<Char, List<Contact>>
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        if (contacts.isEmpty()) {
            item {
                EmptyListContent()
            }
        } else {
            contacts.forEach { (initial, contactsByInitial) ->
                stickyHeader {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    ) {
                        Text(text = initial.toString(), style = MaterialTheme.typography.h5)
                    }
                }
                items(contactsByInitial) { contact: Contact ->
                    ContactCard(contact = contact)
                }
            }
        }

    }
}

@Composable
private fun EmptyListContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(id = R.string.empty_contacts_list_message),
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(100.dp),
        )
    }
}