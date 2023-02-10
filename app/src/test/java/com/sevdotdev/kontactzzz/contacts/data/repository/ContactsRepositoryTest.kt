package com.sevdotdev.kontactzzz.contacts.data.repository

import app.cash.turbine.test
import com.sevdotdev.kontactzzz.contacts.ContactTestFixtures
import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsLocalDataSource
import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsNetworkDataSource
import com.sevdotdev.kontactzzz.contacts.domain.model.ContactsResult
import com.sevdotdev.kontactzzz.core.domain.model.Error
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsRepositoryTest {

    private lateinit var networkDataSource: ContactsNetworkDataSource
    private lateinit var localDataSource: ContactsLocalDataSource
    private lateinit var sysUnderTest: ContactsRepository

    private val logger = TestLogger

    @Test
    fun `repository updates result flow on successful network retrieve and local save`() = runTest {
        val contactList = ContactTestFixtures.getListOfContacts(10)
        networkDataSource = mockk()
        localDataSource = mockk()
        every { localDataSource.contacts } returns flowOf(contactList)
        sysUnderTest = ContactsRepository(
            networkDataSource,
            localDataSource,
            logger,
        )
        sysUnderTest.resultFlow.test {
            coEvery { networkDataSource.getAllContacts() } returns contactList
            every { localDataSource.saveContacts(contactList) } just runs
            sysUnderTest.refreshContactsFromNetwork()
            verify { localDataSource.saveContacts(contactList) }
            assertEquals(
                ContactsResult(contacts = contactList, error = null),
                expectMostRecentItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `repository updates result flow with network error on network failure by catching exception`() =
        runTest {
            networkDataSource = mockk()
            localDataSource = mockk()
            every { localDataSource.contacts } returns flowOf(emptyList())
            sysUnderTest = ContactsRepository(
                networkDataSource,
                localDataSource,
                logger,
            )
            sysUnderTest.resultFlow.test {
                val exception = HttpException(
                    Response.error<String>(
                        400,
                        ResponseBody.create(
                            MediaType.get("application/json"), "error message",
                        ),
                    )
                )
                coEvery { networkDataSource.getAllContacts() } throws exception
                every { localDataSource.contacts } returns flowOf(emptyList())
                sysUnderTest.refreshContactsFromNetwork()
                assertEquals(
                    ContactsResult(emptyList(), Error.NetworkError),
                    expectMostRecentItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }
}