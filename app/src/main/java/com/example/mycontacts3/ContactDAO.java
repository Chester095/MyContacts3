package com.example.mycontacts3;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert //добавление контактов
    void insertContact(Contact contact);

    @Query("Select * FROM contacts_table") // для получения всех контактов
    List<Contact> getAllContacts();

    @Query("SELECT * FROM contacts_table WHERE concatId==:contactId") //метод который возвращает данные
    Contact getContact(Long contactId);

    @Update
    void updateContact(Contact contact);

    @Delete
    void deleteContact(Contact contact);


}
