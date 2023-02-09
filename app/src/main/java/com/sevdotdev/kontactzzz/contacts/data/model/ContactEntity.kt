package com.sevdotdev.kontactzzz.contacts.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class ContactEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "contact_name") val name: String,
    @ColumnInfo(name = "phone_number") val phoneNumber: String,
    @ColumnInfo(name = "email_address") val emailAddress: String,
)
