package com.example.mycontacts3;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1) // должный анотировать этот класс и указать entities какие у нас есть и версию
public abstract class MyContactsDatabase extends RoomDatabase {


}
