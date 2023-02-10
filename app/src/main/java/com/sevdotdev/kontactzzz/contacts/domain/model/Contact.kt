package com.sevdotdev.kontactzzz.contacts.domain.model

/**
 * Domain representation of a Contact, this is what the UI layer will expect. And it is what
 * objects in the data layer must be marshalled to.
 *
 * @property id unique interger id, currently determined by network and mirrored locally
 * @property name contacts name
 * @property phoneNumber string representation of phone number
 * @property emailAddress contacts email
 * @property id computed property, first char of [name]
 */
data class Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String,
    val emailAddress: String
) {
    val initial: Char
        get() = name.first()
}
