package com.sevdotdev.kontactzzz.contacts.data.model


import com.squareup.moshi.Json

data class ContactItemJson(
    @Json(name = "email")
    val email: String,
    @Json(name = "id")
    val id: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "phone")
    val phone: String
)