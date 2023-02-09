package com.sevdotdev.kontactzzz.contacts.data.dao

import androidx.room.*
import com.sevdotdev.kontactzzz.contacts.data.model.ContactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {
    @Query("SELECT * FROM contacts order by contact_name ASC")
    fun getAll(): Flow<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg contacts: ContactEntity)
}
