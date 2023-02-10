package com.sevdotdev.kontactzzz.contacts

import com.sevdotdev.kontactzzz.contacts.data.model.ContactEntity
import com.sevdotdev.kontactzzz.contacts.data.model.ContactItemJson
import com.sevdotdev.kontactzzz.contacts.domain.model.Contact

object ContactTestFixtures {

    private const val defaultName = "Frodo"
    private const val defaultPhone = "123-456-7890"
    private const val defaultEmail = "RingB3ar3r@hobbiton.me"

    internal fun getContactEntity(
        id: Int,
        name: String = defaultName,
        phoneNumber: String = defaultPhone,
        emailAddress: String = defaultEmail,
    ) = ContactEntity(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        emailAddress = emailAddress,
    )

    internal fun getContact(
        id: Int,
        name: String = defaultName,
        phoneNumber: String = defaultPhone,
        emailAddress: String = defaultEmail,
    ) = Contact(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        emailAddress = emailAddress,
    )

    internal fun getContactJsonItem(
        id: Int,
        name: String = defaultName,
        phoneNumber: String = defaultPhone,
        emailAddress: String = defaultEmail,
    ) = ContactItemJson(
        id = id,
        name = name,
        phone = phoneNumber,
        email = emailAddress,
    )

    internal fun getListOfContacts(
        amountOfContacts: Int,
        name: String = defaultName,
        phoneNumber: String = defaultPhone,
        emailAddress: String = defaultEmail,
    ): List<Contact> {
        val contactList = mutableListOf<Contact>()
        repeat(amountOfContacts) { index ->
            contactList.add(
                getContact(
                    id = index,
                    name = name + index,
                    phoneNumber = phoneNumber + index,
                    emailAddress = emailAddress + index,
                )
            )
        }
        return contactList
    }

    internal fun getListOfContactEntity(
        amountOfContacts: Int, name: String = defaultName,
        phoneNumber: String = defaultPhone,
        emailAddress: String = defaultEmail,
    ): List<ContactEntity> {
        val contactEntityList = mutableListOf<ContactEntity>()
        repeat(amountOfContacts) { index ->
            contactEntityList.add(
                getContactEntity(
                    id = index,
                    name = name + index,
                    phoneNumber = phoneNumber + index,
                    emailAddress = emailAddress + index,
                )
            )
        }
        return contactEntityList
    }

    internal fun getListOfContactItemJson(
        amountOfContacts: Int, name: String = defaultName,
        phoneNumber: String = defaultPhone,
        emailAddress: String = defaultEmail,
    ): List<ContactItemJson> {
        val contactItemJsonList = mutableListOf<ContactItemJson>()
        repeat(amountOfContacts) { index ->
            contactItemJsonList.add(
                getContactJsonItem(
                    id = index,
                    name = name + index,
                    phoneNumber = phoneNumber + index,
                    emailAddress = emailAddress + index,
                )
            )
        }
        return contactItemJsonList
    }
}