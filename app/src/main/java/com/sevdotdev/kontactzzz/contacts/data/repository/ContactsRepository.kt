package com.sevdotdev.kontactzzz.contacts.data.repository

import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsLocalDataSource
import com.sevdotdev.kontactzzz.contacts.data.datasource.ContactsNetworkDataSource
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact
import com.sevdotdev.kontactzzz.contacts.domain.model.ContactsResult
import com.sevdotdev.kontactzzz.core.domain.model.Error
import com.sevdotdev.kontactzzz.core.logger.Logger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.combine
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * The repository class is the gateway to the data layer of the Contacts feature and acts as the
 * interface to the outer layers of the application such as the UI. This class is responsible for
 * providing access to the local data source to supply the UI with the Contacts data to display
 * it is also in charge of taking the results of network requests and sending the data to be saved
 * in the local persitence layer.
 *
 * Because of the joint dependencies on both the local and network datasources the [ContactsRepository]
 * is also responsible for propegating errors from either of these sources in a way the UI layer
 * is able to handle them appropriately
 *
 * @property networkDataSource
 * @see [ContactsNetworkDataSource]
 * @property localDataSource
 * @see [ContactsLocalDataSource]
 * @property logger
 * @see [Logger]
 *
 * @property errorFlow of type SharedFlow as it will emit the same value twice, this is necessary
 * as a user could try multiple refresh attempts while the server is down each resulting in
 * a [Error.NetworkError]
 * @property contactsFlow Flow of a list of contacts which comes from [localDataSource]
 * @property resultFlow uses the [combine] operation to join the emissions from
 * [errorFlow] & [contactsFlow] to a flow which with emit a [ContactsResult]
 * Flow diagram:
 * ----A-----------D----- [contactsFlow]
 * -------C-------------- [errorFlow]
 * -------AC-------DC---- [resultFlow]
 */
class ContactsRepository @Inject constructor(
    private val networkDataSource: ContactsNetworkDataSource,
    private val localDataSource: ContactsLocalDataSource,
    private val logger: Logger,
) {

    private val errorFlow: MutableSharedFlow<Error?> = MutableSharedFlow()
    private val contactsFlow: Flow<List<Contact>> = localDataSource.contacts

    val resultFlow = combine(contactsFlow, errorFlow)
    { contacts, error ->
        ContactsResult(contacts = contacts, error = error)
    }

    /**
     * When this function is called it will get all contacts available from the network and
     * then call [saveContacts] to delegate saving them locally. In the case that an error was
     * pushed on a previous attempt, on a successful retrieval and save [null] will be pushed
     * to the [errorFlow] to indicate that there is no error state
     *
     * In the event that an error is present either in getting the contacts from the network, or
     * in saving locally, the exception with be caught then processed to the domain object
     * [Error] and pushed to the [errorFlow]
     */
    suspend fun refreshContactsFromNetwork() {
        try {
            val contacts = networkDataSource.getAllContacts()
            saveContacts(contactsList = contacts)
            errorFlow.emit(null)
        } catch (ex: Exception) {
            //Avoid consuming coroutine cancellation in following block even if unlikely
            if (ex is CancellationException) {
                throw ex
            }
            logger.error(TAG, ex.message, ex)
            errorFlow.emit(
                when (ex) {
                    is HttpException, is IOException -> {
                        Error.NetworkError
                    }
                    else -> {
                        Error.UnknownError
                    }
                }
            )
        }
    }

    /**
     * Save a list of [Contact] to the [ContactsLocalDataSource]
     * This function access is private as it will only recieve data from the
     * [ContactsNetworkDataSource] which is a internal dependency.
     * This access could be changed in the future to open up to saving user created
     * or sync contacts from the device
     */
    private fun saveContacts(contactsList: List<Contact>) {
        localDataSource.saveContacts(contactsList)
    }

    companion object {
        val TAG: String = ContactsRepository::class.java.simpleName
    }
}