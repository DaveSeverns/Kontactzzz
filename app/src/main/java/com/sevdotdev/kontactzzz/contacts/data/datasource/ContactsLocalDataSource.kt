package com.sevdotdev.kontactzzz.contacts.data.datasource

import com.sevdotdev.kontactzzz.contacts.data.dao.ContactDao
import com.sevdotdev.kontactzzz.contacts.data.mapper.toDomain
import com.sevdotdev.kontactzzz.contacts.data.mapper.toLocalData
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

typealias ContactsList = List<Contact>

class ContactsLocalDataSource @Inject constructor(
    private val contactDao: ContactDao
) {
    val contacts: Flow<ContactsList>
        get() = contactDao.getAll().map { entityList ->
            entityList.toDomain()
        }

    fun saveContacts(contactsList: ContactsList) {
        val entityList = contactsList.toLocalData().toTypedArray()
        contactDao.insertAll(*entityList)
    }
}
