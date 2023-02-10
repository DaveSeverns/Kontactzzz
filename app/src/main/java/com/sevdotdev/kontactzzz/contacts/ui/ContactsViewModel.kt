package com.sevdotdev.kontactzzz.contacts.ui

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sevdotdev.kontactzzz.contacts.data.repository.ContactsRepository
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import com.sevdotdev.kontactzzz.core.domain.model.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

typealias ContactsViewState = Map<Char, List<Contact>>

/**
 * This class is responsible for exposing observable data to the UI, by extending
 * [ViewModel] it gains advantage of being lifecylcle aware and maintaining its state across
 * configuration changes. This class is also responsible for mediating access to deeper layer
 * components, both implicitly and explicitly based on events from the UI/View
 * @see [ContactsRepository]
 *
 * @property contactsRepository mentioned above, access point to the data layer
 *
 * @property refreshingViewState instance of compose state with private mutable backing property
 * responsible for exposing to the UI whether or not a refresh of data is taking place
 *
 * @property contactsViewState exposed a grouped map of lists of contacts to the UI
 * @property errorSharedFlow exposes error state to the UI, SharedFlow as it may emit same
 * value repeatedly. Immutable public field, private backing field
 */
@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    private val _refreshingViewState = mutableStateOf(false)
    val refreshingViewState: State<Boolean> = _refreshingViewState


    /**
     * When flow is collected will set [refreshingViewState] to false
     * and map [com.sevdotdev.kontactzzz.contacts.domain.model.ContactsResult] and split
     * error to separate result and pust grouped map out to subscriber
     */
    val contactsViewState: StateFlow<ContactsViewState> by lazy {
        contactsRepository.resultFlow.flowOn(
            Dispatchers.IO
        )
            .onEach {
                _refreshingViewState.value = false
            }.map {result ->
                _errorSharedFlow.emit(result.error)
                result.contacts.groupBy { contact ->
                    contact.initial
                }
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                mapOf(),
            )
    }

    private val _errorSharedFlow: MutableSharedFlow<Error?> = MutableSharedFlow()
    val errorSharedFlow: Flow<Error?> = _errorSharedFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            refreshContacts()
        }
    }

    fun refreshContacts() = viewModelScope.launch {
        _refreshingViewState.value = true
        withContext(Dispatchers.IO) {
            contactsRepository.refreshContactsFromNetwork()
        }
    }
}
