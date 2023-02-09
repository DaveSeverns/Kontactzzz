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

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {
    private val _refreshingViewState = mutableStateOf(false)
    val refreshingViewState: State<Boolean> = _refreshingViewState

    private val _contactsViewState: MutableStateFlow<Map<Char,List<Contact>>> = MutableStateFlow(emptyMap())
    val contactsViewState: StateFlow<Map<Char,List<Contact>>> = _contactsViewState.asStateFlow()

    private val _errorSharedFlow: MutableSharedFlow<Error?> = MutableSharedFlow()
    val errorSharedFlow: Flow<Error?> = _errorSharedFlow.asSharedFlow()

    init {
        viewModelScope.launch {
            refreshContacts()
            contactsRepository.resultFlow.collect { result ->
                val grouped = result.contacts.sortedBy {
                    it.name.first()
                }.groupBy {
                    it.name.first()
                }
                _contactsViewState.emit(grouped)

                _errorSharedFlow.emit(result.error)
            }
        }
    }

    fun refreshContacts() = viewModelScope.launch {
        _refreshingViewState.value = true
        withContext(Dispatchers.IO) {
            contactsRepository.refreshContactsFromNetwork()
        }
        _refreshingViewState.value = false
    }
}
