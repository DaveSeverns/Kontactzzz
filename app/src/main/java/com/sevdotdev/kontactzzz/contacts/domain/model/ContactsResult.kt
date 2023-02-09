package com.sevdotdev.kontactzzz.contacts.domain.model

import com.sevdotdev.kontactzzz.core.domain.model.Error

data class ContactsResult(
    val contacts: List<Contact>,
    val error: Error? = null
)