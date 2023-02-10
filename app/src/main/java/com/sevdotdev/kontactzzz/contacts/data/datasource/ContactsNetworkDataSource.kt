package com.sevdotdev.kontactzzz.contacts.data.datasource

import com.sevdotdev.kontactzzz.contacts.data.ContactsApi
import com.sevdotdev.kontactzzz.contacts.data.mapper.toDomain
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import javax.inject.Inject

/**
 * This class serves a similar role to [ContactsLocalDataSource] with the key distinction that
 * it allows only for read access to contacts from the network server. Key responsibiltiy of this
 * is to return data from the network and transforming it to adhere to the [Contact] domain object
 *
 * @property contactsApi network interface provided via retrofit
 */
class ContactsNetworkDataSource @Inject constructor(
    private val contactsApi: ContactsApi,
) {
    suspend fun getAllContacts(): List<Contact> = contactsApi.getContacts().toDomain()
}