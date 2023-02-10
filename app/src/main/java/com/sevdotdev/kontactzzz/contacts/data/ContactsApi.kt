package com.sevdotdev.kontactzzz.contacts.data

import com.sevdotdev.kontactzzz.contacts.data.model.ContactItemJson
import retrofit2.http.GET
interface ContactsApi {

    @GET("users")
    suspend fun getContacts(): List<ContactItemJson>
}