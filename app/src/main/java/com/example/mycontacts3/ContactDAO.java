package com.example.mycontacts3;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert //lj,
    void insertContact(Contact contact);

    @Query("Select * FROM contacts_table") // для получения всех контактов
    List<Contact> getAllContacts();

    @Query("SELECT * FROM contacts_table WHERE concatId==:contactId") //
    Contact getContact(Long contactId)

}
