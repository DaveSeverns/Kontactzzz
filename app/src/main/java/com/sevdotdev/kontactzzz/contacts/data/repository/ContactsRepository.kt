package com.sevdotdev.kontactzzz.contacts.data.repository

import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsList
import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsLocalDataSource
import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsNetworkDataSource
import com.sevdotdev.kontactzzz.contacts.domain.model.ContactsResult
import com.sevdotdev.kontactzzz.core.domain.model.Error
import com.sevdotdev.kontactzzz.core.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val networkDataSource: ContactsNetworkDataSource,
    private val localDataSource: ContactsLocalDataSource,
    private val logger: Logger,
) {

    private val errorFlow: MutableSharedFlow<Error?> = MutableSharedFlow()
    private val contactsFlow: Flow<ContactsList> = localDataSource.contacts

    val resultFlow = combine(contactsFlow, errorFlow)
    { contacts, error ->
        ContactsResult(contacts = contacts, error = error)
    }

    suspend fun refreshContactsFromNetwork() {
        try {
            val contacts = networkDataSource.getAllContacts()
            saveContacts(contactsList = contacts)
            errorFlow.emit(null)
        } catch (ex: Exception) {
            logger.error(this::class.java.simpleName, ex.message, ex)
            errorFlow.emit(
                when (ex) {
                    is HttpException, is IOException -> {
                        Error.NetworkError
                    }
                    else -> {
                        Error.UnknownError
                    }
                }
            )
        }
    }

    private fun saveContacts(contactsList: ContactsList) {
        localDataSource.saveContacts(contactsList)
    }
}