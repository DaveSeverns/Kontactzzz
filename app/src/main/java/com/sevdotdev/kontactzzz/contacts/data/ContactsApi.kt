package com.sevdotdev.kontactzzz.contacts.data

import com.sevdotdev.kontactzzz.contacts.data.model.ContactItemJson
import retrofit2.http.GET

typealias ContactsJson = List<ContactItemJson>

interface ContactsApi {

    @GET("users")
    suspend fun getContacts(): ContactsJson
}