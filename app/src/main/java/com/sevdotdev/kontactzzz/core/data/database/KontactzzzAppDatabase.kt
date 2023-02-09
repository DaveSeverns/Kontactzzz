package com.sevdotdev.kontactzzz.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sevdotdev.kontactzzz.contacts.data.dao.ContactDao
import com.sevdotdev.kontactzzz.contacts.data.model.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class KontactzzzAppDatabase : RoomDatabase() {
    abstract fun contactDao(): ContactDao
}