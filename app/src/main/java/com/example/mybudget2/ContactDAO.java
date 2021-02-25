package com.example.mybudget2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert //добавление контактов
    void insertContact(Budget budget);

    @Query("Select * FROM budget_table") // для получения всех контактов
    List<Budget> getAllContacts();

    @Query("SELECT * FROM budget_table WHERE contactId==:contactId") //метод который возвращает данные
    Budget getContact(Long contactId);

    @Update
    void updateContact(Budget budget);

    @Delete
    void deleteContact(Budget budget);


}
