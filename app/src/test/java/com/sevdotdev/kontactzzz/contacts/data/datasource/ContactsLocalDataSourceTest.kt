package com.sevdotdev.kontactzzz.contacts.data.datasource

import app.cash.turbine.test
import com.sevdotdev.kontactzzz.contacts.ContactTestFixtures
import com.sevdotdev.kontactzzz.contacts.data.dao.ContactDao
import com.sevdotdev.kontactzzz.contacts.data.mapper.toLocalData
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsLocalDataSourceTest {

    private lateinit var mockDao: ContactDao
    private lateinit var sysUnderTest: ContactsLocalDataSource

    @Before
    fun setup() {
        mockDao = mockk()
        sysUnderTest = ContactsLocalDataSource(contactDao = mockDao)
    }

    @Test
    fun `datasource should return flow of list of contacts`() = runTest {
        every { mockDao.getAll() } returns flowOf(ContactTestFixtures.getListOfContactEntity(1))
        val result = sysUnderTest.contacts
        verify { mockDao.getAll() }
        result.test {
            assertEquals(ContactTestFixtures.getListOfContacts(1), awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `datasource should return empty list`() = runTest {
        every { mockDao.getAll() } returns flowOf(emptyList())
        val result = sysUnderTest.contacts
        verify { mockDao.getAll() }
        result.test {
            assertEquals(emptyList<List<Contact>>(), awaitItem())
            awaitComplete()
        }
    }

    @Test(expected = Exception::class)
    fun `datasource throws exception from dao getting contacts`() {
        every { mockDao.getAll() } throws Exception("Test")
        sysUnderTest.contacts
        // Should never reach this line, or will fail
        assert(false)
    }

    @Test
    fun `datasource calls dao save function successfully`() {
        every { mockDao.insertAll(*anyVararg()) } just (runs)
        val list = ContactTestFixtures.getListOfContacts(24)
        sysUnderTest.saveContacts(contactsList = list)
        verify { mockDao.insertAll(*list.toLocalData().toTypedArray()) }
    }

    @Test(expected = Exception::class)
    fun `datasource throws exception from dao saving contacts`() {
        every { mockDao.insertAll(*anyVararg()) } throws Exception("Test")
        sysUnderTest.saveContacts(emptyList())
        // Should never reach this line, or will fail
        assert(false)
    }

}