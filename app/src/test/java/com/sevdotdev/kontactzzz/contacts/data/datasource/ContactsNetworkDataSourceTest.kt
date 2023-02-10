package com.sevdotdev.kontactzzz.contacts.data.datasource

import com.sevdotdev.kontactzzz.contacts.ContactTestFixtures
import com.sevdotdev.kontactzzz.contacts.data.ContactsApi
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsNetworkDataSourceTest {

    private lateinit var mockApi: ContactsApi
    private lateinit var sysUnderTest: ContactsNetworkDataSource

    @Before
    fun setup() {
        mockApi = mockk()
        sysUnderTest = ContactsNetworkDataSource(contactsApi = mockApi)
    }

    @Test
    fun `datasource should get list of contacts from api`() = runTest{
        coEvery { mockApi.getContacts() } returns ContactTestFixtures.getListOfContactItemJson(10)
        val result = sysUnderTest.getAllContacts()
        coVerify { mockApi.getContacts() }

        assertEquals(ContactTestFixtures.getListOfContacts(10), result)
    }

    @Test(expected = Exception::class)
    fun `datasource should propagate api exception`() = runTest{
        coEvery { mockApi.getContacts() } throws Exception("Test")
        sysUnderTest.getAllContacts()
        coVerify { mockApi.getContacts() }
        // Should not reach this line, else will fail test
        assert(false)
    }
}