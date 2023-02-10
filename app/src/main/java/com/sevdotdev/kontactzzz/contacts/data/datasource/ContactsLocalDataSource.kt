package com.sevdotdev.kontactzzz.contacts.data.datasource

import com.sevdotdev.kontactzzz.contacts.data.dao.ContactDao
import com.sevdotdev.kontactzzz.contacts.data.mapper.toDomain
import com.sevdotdev.kontactzzz.contacts.data.mapper.toLocalData
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * This class represents access to the application's single source of truth for the list of
 * contacts. Encapsulating the implementaion of [ContactDao] clients of this class are ignorant
 * whether the local data source is on disk or in memory. The local data source allows for
 * dependencies to read and write to the SST without direct access. ContactsLocalDataSource's
 * API allows for contacts coming from different sources to be synced to one source of truth
 * provided those contacts are marshalled to the domain object [Contact]
 *
 * @property contactDao: [ContactDao] using Room database SQLite implementation under the hood.
 */
class ContactsLocalDataSource @Inject constructor(
    private val contactDao: ContactDao
) {
    val contacts: Flow<List<Contact>>
        get() = contactDao.getAll().map { entityList ->
            entityList.toDomain()
        }

    fun saveContacts(contactsList: List<Contact>) {
        val entityList = contactsList.toLocalData().toTypedArray()
        contactDao.insertAll(*entityList)
    }
}
