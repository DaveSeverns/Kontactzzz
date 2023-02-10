package com.sevdotdev.kontactzzz.contacts.domain.model

import com.sevdotdev.kontactzzz.core.domain.model.Error

/**
 * This is a wrapper object around the [Contact] domain object and the
 * core domain [Error] object intended use is for clients who are able to still display a list
 * of contacts but also notify user of potential errors coming from accessing the data layer
 *@see [com.sevdotdev.kontactzzz.contacts.ui.ContactsViewModel]
 */
data class ContactsResult(
    val contacts: List<Contact>,
    val error: Error? = null
)