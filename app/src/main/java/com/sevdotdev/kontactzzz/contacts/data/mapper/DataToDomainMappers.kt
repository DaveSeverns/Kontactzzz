package com.sevdotdev.kontactzzz.contacts.data.mapper

import com.sevdotdev.kontactzzz.contacts.data.model.ContactEntity
import com.sevdotdev.kontactzzz.contacts.data.model.ContactItemJson
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact

@JvmName("entityToDomain")
internal fun List<ContactEntity>.toDomain(): List<Contact> =
    map { contactEntity ->
        contactEntity.toDomain()
    }


internal fun ContactEntity.toDomain(): Contact = Contact(
    id = this.id,
    name = this.name,
    phoneNumber = this.phoneNumber,
    emailAddress = this.emailAddress
)

@JvmName("jsonToDomain")
internal fun List<ContactItemJson>.toDomain(): List<Contact> = map { contactItemJson ->
    contactItemJson.toDomain()
}

internal fun ContactItemJson.toDomain() = Contact(
    id = this.id,
    name = this.name,
    phoneNumber = this.phone,
    emailAddress = this.email,
)
