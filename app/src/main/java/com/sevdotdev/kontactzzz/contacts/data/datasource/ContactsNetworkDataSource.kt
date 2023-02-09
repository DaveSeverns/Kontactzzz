package com.sevdotdev.kontactzzz.contacts.data.datasource

import com.sevdotdev.kontactzzz.contacts.data.ContactsApi
import com.sevdotdev.kontactzzz.contacts.data.mapper.toDomain
import javax.inject.Inject

class ContactsNetworkDataSource @Inject constructor(
    private val contactsApi: ContactsApi,
) {
    suspend fun getAllContacts(): ContactsList = contactsApi.getContacts().toDomain()
}