package com.sevdotdev.kontactzzz.contacts.data.mapper

import com.sevdotdev.kontactzzz.contacts.ContactTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class DomainToDataMappersTest {

    @Test
    fun `should map domain object to database entity`() {
        val id = 99
        val databaseObject = ContactTestFixtures.getContactEntity(
            id = id,
        )
        val domainObject = ContactTestFixtures.getContact(
            id = id,
        )

        assertEquals(databaseObject, domainObject.toEntity())
    }

    @Test
    fun `should map domain object list to list of data base entity objects`() {
        val name = "Samwise"
        val sizeOfList = 10
        val databaseObjectList = ContactTestFixtures.getListOfContactEntity(
            amountOfContacts = sizeOfList,
            name = name,
        )
        val domainObject = ContactTestFixtures.getListOfContacts(
            amountOfContacts = sizeOfList,
            name = name,
        )

        assertEquals(databaseObjectList, domainObject.toLocalData())
    }
}