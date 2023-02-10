package com.sevdotdev.kontactzzz.contacts.data.mapper

import com.sevdotdev.kontactzzz.contacts.ContactTestFixtures
import org.junit.Assert.assertEquals
import org.junit.Test

class DataToDomainMappersTest {
    @Test
    fun `should map json data to domain data object`() {
        val id = 128
        val domainObject = ContactTestFixtures.getContact(
            id = id,
        )
        val dataObject = ContactTestFixtures.getContactJsonItem(
            id = id,
        )

        assertEquals(domainObject, dataObject.toDomain())
    }

    @Test
    fun `should map entity data to domain data object`() {
        val id = 202
        val domainObject = ContactTestFixtures.getContact(
            id = id,
        )
        val dataObject = ContactTestFixtures.getContactEntity(
            id = id,
        )

        assertEquals(domainObject, dataObject.toDomain())
    }

    @Test
    fun `should map list of entity data to list of domain data`() {
        val sizeOfList = 7
        val domainList = ContactTestFixtures.getListOfContacts(
            amountOfContacts = sizeOfList,
        )
        val dataList = ContactTestFixtures.getListOfContactEntity(
            amountOfContacts = sizeOfList,
        )

        assertEquals(domainList, dataList.toDomain())
    }

    @Test
    fun `should map list of json data to list of domain data`() {
        val sizeOfList = 7
        val domainList = ContactTestFixtures.getListOfContacts(
            amountOfContacts = sizeOfList,
        )
        val dataList = ContactTestFixtures.getListOfContactItemJson(
            amountOfContacts = sizeOfList,
        )

        assertEquals(domainList, dataList.toDomain())
    }
}