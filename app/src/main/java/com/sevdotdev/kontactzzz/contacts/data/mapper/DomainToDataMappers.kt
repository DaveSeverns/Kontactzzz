package com.sevdotdev.kontactzzz.contacts.data.mapper

import com.sevdotdev.kontactzzz.contacts.data.model.ContactEntity
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact

internal fun List<Contact>.toLocalData(): List<ContactEntity> = map { domainContact ->
    domainContact.toEntity()
}

internal fun Contact.toEntity() = ContactEntity(
    id = this.id,
    name = this.name,
    phoneNumber = this.phoneNumber,
    emailAddress = this.emailAddress,
)