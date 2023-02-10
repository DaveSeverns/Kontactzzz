package com.sevdotdev.kontactzzz.contacts.ui

import app.cash.turbine.test
import com.sevdotdev.kontactzzz.contacts.ContactTestFixtures
import com.sevdotdev.kontactzzz.contacts.data.repository.ContactsRepository
import com.sevdotdev.kontactzzz.contacts.domain.model.ContactsResult
import com.sevdotdev.kontactzzz.core.domain.model.Error
import com.sevdotdev.kontactzzz.rules.TestCoroutineRule
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ContactsViewModelTest {

    @MockK
    lateinit var repository: ContactsRepository

    @InjectMockKs
    lateinit var viewModelUnderTest: ContactsViewModel

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `refreshingState updates when refreshContacts is called`() = runTest {
        every { repository.resultFlow } returns flowOf(
            ContactsResult(
                contacts = ContactTestFixtures.getListOfContacts(1),
                error = null
            )
        )
        coEvery { repository.refreshContactsFromNetwork() } just runs
        viewModelUnderTest.refreshContacts()
        coVerify { repository.refreshContactsFromNetwork() }
        val result = viewModelUnderTest.refreshingViewState.value
        assert(result)
    }

    @Test
    fun `error flow updates when non null error is collected in resultFlow`() = runTest {
        coEvery { repository.refreshContactsFromNetwork() } just runs
        every { repository.resultFlow } returns flowOf(
            ContactsResult(
                contacts = ContactTestFixtures.getListOfContacts(1),
                error = Error.NetworkError,
            )
        )
        viewModelUnderTest.errorSharedFlow.test {
            viewModelUnderTest.contactsViewState.test {
                cancelAndIgnoreRemainingEvents()
            }
            assertEquals(Error.NetworkError, expectMostRecentItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
   @Ignore("Test is flakey at best, need to dig into the ")
    fun `contacts StateFlow updates when new contacts are emitted by repository`() = runTest {
        val contacts = ContactTestFixtures.getListOfContacts(2)
        every { repository.resultFlow } returns flowOf(
            ContactsResult(
                contacts = contacts
            )
        )
        viewModelUnderTest.contactsViewState.test {
            assertEquals(contacts.groupBy { it.initial }, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
        coEvery { repository.refreshContactsFromNetwork() } just runs
    }
}